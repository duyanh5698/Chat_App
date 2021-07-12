package com.clone.instagram.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clone.instagram.authservice.model.Group;
import com.clone.instagram.authservice.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;
	

	public Group save(Group group) {
		return groupRepository.save(group);
	}

	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	public List<Group> findById(List<String> ids) {
		return groupRepository.findByIdIn(ids);
	}

}
