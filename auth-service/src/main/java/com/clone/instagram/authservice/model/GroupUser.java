package com.clone.instagram.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class GroupUser {

	@Id
	private String id;
	
	@NotNull
	private String groupId;
	
	@NotNull
	private String userId;
}
