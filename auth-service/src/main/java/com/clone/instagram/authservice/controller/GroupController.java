package com.clone.instagram.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clone.instagram.authservice.model.Group;
import com.clone.instagram.authservice.payload.ApiResponse;
import com.clone.instagram.authservice.payload.GroupCreateDto;
import com.clone.instagram.authservice.payload.UserSummary;
import com.clone.instagram.authservice.service.GroupService;
import com.clone.instagram.authservice.service.GroupUserService;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupUserService groupUserService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createGroup(@RequestBody GroupCreateDto groupData){
		List<UserSummary> users = groupData.getUsers();
		if (!users.isEmpty()) {
			Group group = new Group();
			group.setGroupName(groupData.getGroupName());
			group = groupService.save(group);
			groupUserService.save(users, group.getId());
			return ResponseEntity.ok(new ApiResponse(true,"Group created successfully"));
		}
		return ResponseEntity.ok(new ApiResponse(false,"Fail to create group"));
	}
	
	@GetMapping("/listId/{groupId}")
	public ResponseEntity<?> getRecipientList(@PathVariable String groupId){
		return ResponseEntity.ok(groupUserService.getRecipientList(groupId));
	}
}
