package com.kth.board.domain.member.service;

import com.kth.board.domain.member.domain.Authority;
import com.kth.board.domain.member.domain.Member;
import com.kth.board.domain.member.dto.SignRequest;
import com.kth.board.domain.member.dto.SignResponse;
import com.kth.board.domain.member.repository.MemberRepository;
import com.kth.board.global.security.JwtProvider;
import com.kth.board.global.security.Token;
import com.kth.board.global.security.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public SignResponse login(SignRequest request) throws Exception {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .token(TokenDto.builder().access_token(jwtProvider.createToken(member.getAccount(), member.getRoles())).refresh_token(jwtProvider.createRefreshToken(member)).build())
                .build();
    }

    public boolean register(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .email(request.getEmail())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public SignResponse getMember(String account) throws Exception {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponse(member);
    }

    public Optional<Member> findMember(String account){
        Optional<Member> member = memberRepository.findByAccount(account);
        return member;
    }


    public TokenDto refreshAccessToken(TokenDto token) throws Exception {
        String account = jwtProvider.getAccount(token.getAccess_token());
        Member member = memberRepository.findByAccount(account).orElseThrow(() -> new BadCredentialsException("잘못된 계정 정보입니다"));
        Token refreshToken = jwtProvider.validRefreshToken(member, token.getRefresh_token());

        if(refreshToken != null){
            return TokenDto.builder()
                    .access_token(jwtProvider.createToken(account, member.getRoles()))
                    .refresh_token(refreshToken.getRefresh_token())
                    .build();
        }else {
            throw new Exception("로그인을 해주세요");
        }
    }

}
