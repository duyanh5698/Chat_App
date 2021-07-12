package com.clone.instagram.authservice.payload;

import java.util.List;

import lombok.Data;

@Data
public class GroupCreateDto {
	
	private String groupName;
	
	private List<UserSummary> users;
}
