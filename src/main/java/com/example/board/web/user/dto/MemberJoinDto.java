package com.example.board.web.user.dto;

import com.example.board.domain.user.Member;
import com.example.board.domain.user.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberJoinDto {
    private String memberId;
    private String password;
    private String email;

    private Role role;


    public Member toEntity(){
        Member member = Member.builder()
                .memberId(memberId)
                .password(password)
                .email(email)
                .role(role.USER)
                .build();
        return member;
    }

    /* 암호화된 password */
    public void encryptPassword(String BCryptpassword) {
        this.password = BCryptpassword;
    }
}
