package com.bookmakase.dto.user;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationUpdateRequest {
	private String currentPassword;

	@Size(min = 8)
	private String newPassword;
	private String confirmNewPassword;

	@Size(min = 2, max = 10)
	private String newUsername;
}
