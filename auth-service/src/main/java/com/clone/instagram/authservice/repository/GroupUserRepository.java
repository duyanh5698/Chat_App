package com.clone.instagram.authservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.clone.instagram.authservice.model.Group;
import com.clone.instagram.authservice.model.GroupUser;

@Repository
public interface GroupUserRepository extends MongoRepository<GroupUser, String>{
	List<GroupUser> findByGroupId(String groupId);
	List<GroupUser> findByUserId(String userId);
	List<GroupUser> findByGroupIdAndUserId(String groupId, String userId);
	
//	@Query("SELECT DISTINCT g FROM GroupUser ge JOIN Group g ON ge.groupId = g.groupId WHERE ge.userId =:userId")
//	List<Group> findGroupByUserId(String userId);

}
