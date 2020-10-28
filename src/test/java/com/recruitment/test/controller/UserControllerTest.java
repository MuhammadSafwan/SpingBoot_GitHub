package com.recruitment.test.controller;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Safwan
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Mock
    org.springframework.web.servlet.View mockView;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user = new User();
    private User mutualFollowingUser = new User();
    private User followingUser = new User();
    private List<User> userList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setSingleView(mockView).build();
        userList.add(mutualFollowingUser);
        userList.add(followingUser);
    }

    @Test
    public void shallReturnOkResponse() throws Exception {
        User user = new User();
        user.setId(123);
        when(userService.getUser(anyString())).thenReturn(user);
        mockMvc.perform(get("/github-user-profile/user/Test"))
                .andExpect(status().isOk());
    }

    @Test
    public void shallReturnNotFound() throws Exception {
        when(userService.getUser(anyString())).thenReturn(null);
        mockMvc.perform(get("/github-user-profile/use/Test"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shallReturnOkForFollowers() throws Exception {
        when(userService.getFollowers(anyString())).thenReturn(userList);
        mockMvc.perform(get("/github-user-profile/user/Test/followers"))
                .andExpect(status().isOk());
    }

    @Test
    public void shallReturnOkForMutualFollowers() throws Exception {
        when(userService.getFollowing(anyString())).thenReturn(userList);
        mockMvc.perform(get("/github-user-profile/user/Test/mutualFollowers"))
                .andExpect(status().isOk());
    }
}
