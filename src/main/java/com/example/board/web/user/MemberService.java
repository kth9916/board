package com.example.board.web.user;

import com.example.board.config.security.JwtTokenProvider;
import com.example.board.domain.user.Member;
import com.example.board.domain.user.MemberRepository;
import com.example.board.web.user.dto.MemberJoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    //
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder encoder;

    @Transactional
    public TokenInfo login(String memberId, String password){
        // 1. Login ID/PW를 기반으로 Authentication 객체 생성
        // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUserName 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰을 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    /* 회원가입 */
    public String userJoin(MemberJoinDto dto) {
        /* 비밀번호 암호화 */
        dto.encryptPassword(encoder.encode(dto.getPassword()));

        Member member = dto.toEntity();
        memberRepository.save(member);
        log.info("DB에 회원 저장 성공");

        return member.getMemberId();
    }

}
