package com.bookmakase.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;


    @NotBlank(message = "사용자 이름은 필수입니다.")
    @Size(min = 2, max = 10, message = "사용자 이름은 2–10자 사이여야 합니다.")
    private String username;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$",
            message = "전화번호는 000-0000-0000 형식이어야 합니다.")
    private String phone;


}
