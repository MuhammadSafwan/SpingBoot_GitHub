package com.recruitment.test.controller;


import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Safwan
 */

@RestController
@RequestMapping("/github-user-profile/user")
public class UserController {

    @Autowired
    private UserService userService;

    List<User> userFollowers;
    List<User> userFollowings;
    List<Integer> followingUserIds;

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws IOException {
        User user = userService.getUser(username);
        return user;
    }

    @GetMapping("/{username}/followers")
    public List<User> getUsersFollowed(@PathVariable String username) throws IOException {
        userFollowers = userService.getFollowers(username);
        userFollowings = userService.getFollowing(username);

        followingUserIds = getIdsOfUsersFollowing(userFollowings);
        return userFollowers.stream()
                .filter(follower -> !followingUserIds.contains(follower.getId()))
                .collect(Collectors.toList());
    }


    @GetMapping("/{username}/mutualFollowers")
    public List<User> getUsersFollowing(@PathVariable String username) throws IOException {
        userFollowings = userService.getFollowing(username);
        userFollowers = userService.getFollowers(username);

        followingUserIds = getIdsOfUsersFollowing(userFollowings);
        return userFollowers.stream()
                .filter(follower -> followingUserIds.contains(follower.getId()))
                .collect(Collectors.toList());
    }

    private List<Integer> getIdsOfUsersFollowing(List<User> following) {
        List<Integer> followingUserIds = following.stream()
                .map(followingUser -> followingUser.getId())
                .collect(Collectors.toList());
        return followingUserIds;
    }
}
