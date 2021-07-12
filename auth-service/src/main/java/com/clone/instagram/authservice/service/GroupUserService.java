package com.clone.instagram.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clone.instagram.authservice.payload.GroupResponse;
import com.clone.instagram.authservice.payload.UserSummary;
import com.clone.instagram.authservice.model.Group;
import com.clone.instagram.authservice.model.GroupUser;
import com.clone.instagram.authservice.repository.GroupUserRepository;

@Service
public class GroupUserService {

	@Autowired
	private GroupUserRepository groupUserRepository;


	public void save(List<UserSummary> users, String groupId) {
			users.forEach(user -> {
				GroupUser groupUser = new GroupUser();
				groupUser.setUserId(user.getId());
				groupUser.setGroupId(groupId);
				groupUserRepository.save(groupUser);
			});
	}
	
//	public List<String> getRecipientIdList(String groupId){
//		List<String> recipientIdList = new ArrayList<>();
//		List<GroupUser> groupUsers = groupUserRepository.findByGroupId(groupId);
//		recipientIdList = groupUsers.stream().map(i -> i.getUserId()).collect(Collectors.toList());
//		return recipientIdList;
//	}
	
	public GroupResponse getRecipientList(String groupId){
		GroupResponse recipientList = new GroupResponse();
		List<String> recipientIdList = new ArrayList<>();
		List<GroupUser> groupUsers = groupUserRepository.findByGroupId(groupId);
		recipientIdList = groupUsers.stream().map(i -> i.getUserId()).collect(Collectors.toList());
		recipientList.setGroupId(groupId);
		recipientList.setUserIdList(recipientIdList);
		return recipientList;
	}
	
	public List<GroupUser> findByGroupIdAndUserId(String groupId, String userId) {
		return groupUserRepository.findByGroupIdAndUserId(groupId, userId);
	}
	
	public List<GroupUser> findByUserId(String userId) {
		return groupUserRepository.findByUserId(userId);
	}
	
//	public List<Group> findGroupByUserId(String userId){
//		return groupUserRepository.findGroupByUserId(userId);
//	}
}
