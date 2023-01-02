package com.example.board.web.user;

import com.example.board.web.user.dto.MemberJoinDto;
import com.example.board.web.user.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/auth/join")
    public String join() {
        return "/user/user-join";
    }

    @PostMapping("/auth/joinProc")
    public String joinProc(@Valid MemberJoinDto userDto, BindingResult bindingResult, Model model) {

        /* 검증 */
        if (bindingResult.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("userDto", userDto);

            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
                log.info("회원가입 실패 ! error message : " + error.getDefaultMessage());
            }

            /* Model에 담아 view resolve */
            for (String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            /* 회원가입 페이지로 리턴 */
            return "/user/user-join";
        }

        // 회원가입 성공 시
        memberService.userJoin(userDto);
        log.info("회원가입 성공");
        return "redirect:/auth/login";
    }
}
