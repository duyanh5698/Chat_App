package com.clone.instagram.authservice.payload;

import java.util.List;

import lombok.Data;

@Data
public class GroupResponse {
	private String groupId;
	private List<String> userIdList;
}
