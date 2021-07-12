package com.clone.instagram.authservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.clone.instagram.authservice.model.Group;
import com.clone.instagram.authservice.model.GroupUser;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

	List<Group> findByIdIn(List<String> id);
}
