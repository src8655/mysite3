package com.cafe24.mysite.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.config.TestWebConfig;
import com.cafe24.mysite.vo.GuestbookVo;
import com.cafe24.mysite.vo.UserVo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class UserControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	@Test
	public void testUserJoin() throws Exception {
		ResultActions resultActions;
		
		UserVo userVo = new UserVo();

		// 1. Normal User's Join Data
		userVo.setName("윤민호");
		userVo.setEmail("src8655@naver.com");
		userVo.setPassword("snrnsnrn1!");
		userVo.setGender("MALE");
		
		resultActions = mockMvc.perform(post("/api/user/join").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions
		.andExpect(status().isOk())
		.andDo(print());
		
		

		// 2. InValidation in Name
		userVo.setName("윤");
		userVo.setEmail("src8655@naver.com");
		userVo.setPassword("snrnsnrn1!");
		userVo.setGender("MALE");
		
		resultActions = mockMvc.perform(post("/api/user/join").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions
		.andExpect(status().isBadRequest())
		.andDo(print());
		
		

		// 3. InValidation in Password
		userVo.setName("윤민호");
		userVo.setEmail("src8655@naver.com");
		userVo.setPassword("1234");
		userVo.setGender("MALE");
		
		resultActions = mockMvc.perform(post("/api/user/join").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions
		.andExpect(status().isBadRequest())
		.andDo(print());
		
		
		
		// 4. InValidation in Password
		userVo.setName("윤민호");
		userVo.setEmail("src8655@naver.com");
		userVo.setPassword("snrnsnrn1!");
		userVo.setGender("남자");
		
		resultActions = mockMvc.perform(post("/api/user/join").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions
		.andExpect(status().isBadRequest())
		.andDo(print());
		
	}
	
	
	@Test
	public void testUserLogin() throws Exception {
		ResultActions resultActions;
		
		UserVo userVo = new UserVo();

		// 1. Normal User's Login Data
		userVo.setEmail("src8655@naver.com");
		userVo.setPassword("snrnsnrn1!");
		
		resultActions = mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions
		.andExpect(status().isOk())
		.andDo(print());
		

		// 2. InValidation in Email
		userVo.setEmail("src8655");
		userVo.setPassword("snrnsnrn1!");
		
		resultActions = mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));

		resultActions
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
}
