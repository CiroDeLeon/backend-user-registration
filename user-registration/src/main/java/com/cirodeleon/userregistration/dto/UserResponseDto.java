package com.cirodeleon.userregistration.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
	 private UUID id;
	 private String name;
	 private String email;
	 private Date created;
	 private Date modified;
	 private Date lastLogin;
	 private String token;
	 private boolean isActive;
}
