package com.clone.instagram.authservice.endpoint;

import com.clone.instagram.authservice.config.TypeChatEnum;
import com.clone.instagram.authservice.exception.*;
import com.clone.instagram.authservice.model.Group;
import com.clone.instagram.authservice.model.GroupUser;
import com.clone.instagram.authservice.model.InstaUserDetails;
import com.clone.instagram.authservice.model.User;
import com.clone.instagram.authservice.payload.*;
import com.clone.instagram.authservice.repository.GroupUserRepository;
import com.clone.instagram.authservice.service.GroupService;
import com.clone.instagram.authservice.service.GroupUserService;
import com.clone.instagram.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserEndpoint {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupUserService groupUserService;

	@GetMapping(value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findUser(@PathVariable("username") String username) {
		log.info("retrieving user {}", username);

		return userService.findByUsername(username).map(user -> ResponseEntity.ok(user))
				.orElseThrow(() -> new ResourceNotFoundException(username));
	}

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		log.info("retrieving all users");

		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping(value = "/users/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAllUserSummaries(@AuthenticationPrincipal InstaUserDetails userDetails) {
		log.info("retrieving all users summaries");
		List<String> gIdStrings = groupUserService.findByUserId(userDetails.getId()).stream().map(GroupUser::getGroupId)
				.distinct().collect(Collectors.toList());
		List<Group> group1s = groupService.findById(gIdStrings);

		Stream<UserSummary> groups = group1s.stream().map(this::convertGroupTo);
		Stream<UserSummary> users = userService.findAll().stream()
				.filter(user -> !user.getUsername().equals(userDetails.getUsername())).map(this::convertTo);
		Stream<UserSummary> userSummaries = Stream.concat(users, groups);
		return ResponseEntity.ok(userSummaries);
	}

	@GetMapping(value = "/users/me", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER') or hasRole('FACEBOOK_USER')")
	@ResponseStatus(HttpStatus.OK)
	public UserSummary getCurrentUser(@AuthenticationPrincipal InstaUserDetails userDetails) {
		return UserSummary.builder().id(userDetails.getId()).username(userDetails.getUsername())
				.name(userDetails.getUserProfile().getDisplayName())
				.profilePicture(userDetails.getUserProfile().getProfilePictureUrl()).build();
	}

	@GetMapping(value = "/users/summary/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserSummary(@PathVariable("username") String username) {
		log.info("retrieving user {}", username);

		return userService.findByUsername(username).map(user -> ResponseEntity.ok(convertTo(user)))
				.orElseThrow(() -> new ResourceNotFoundException(username));
	}

	private UserSummary convertGroupTo(Group group) {
		return UserSummary.builder().id(group.getId()).username(group.getGroupName()).name(group.getGroupName())
				.type(TypeChatEnum.GROUP).build();
	}

	private UserSummary convertTo(User user) {
		return UserSummary.builder().id(user.getId()).username(user.getUsername())
				.name(user.getUserProfile().getDisplayName())
				.profilePicture(user.getUserProfile().getProfilePictureUrl()).type(TypeChatEnum.USER).build();
	}
}