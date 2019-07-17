package com.cafe24.mysite.controller.api;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class GuestbookControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	//Guestbook에서 데이터를 10개 가져오는 것을 테스트
	@Test
	public void testFetchGuestbookList() throws Exception {
		
		// api로 요청하고나서 바라는 것이 isOk이다
		// 그리고 나서 응답내용을 보자
		// 바라는 결과
		// "result":"success",
		// "message":null,
		// "data:[{"no":1,
		//		   "name":"user1",
		//		   "password":"password1",
		//		   "contents":"contents1",
		// 		   "regDate":"regDate1"
		// 		  },
		// 		  {"no":2,
		//  	   "name":"user2",
		//  	   "password":"password2",
		//  	   "contents":"contents2",
		//  	   "regDate":"regDate2"
		// 		  }]
		ResultActions resultActions = mockMvc.perform(get("/api/guestbook/list/{no}", 1L).contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")))
		.andExpect(jsonPath("$.data", hasSize(2)))
		.andExpect(jsonPath("$.data[0].name", is("user1")))
		.andExpect(jsonPath("$.data[0].password", is("password1")))
		.andExpect(jsonPath("$.data[0].contents", is("contents1")))
		.andExpect(jsonPath("$.data[1].name", is("user2")))
		.andExpect(jsonPath("$.data[1].password", is("password2")))
		.andExpect(jsonPath("$.data[1].contents", is("contents2")));
		
	}
	
	@Test
	public void testInsertGuestbook() throws Exception {
		GuestbookVo vo = new GuestbookVo();
		vo.setName("user1");
		vo.setPassword("1234");
		vo.setContents("test1");
		// 이걸 json으로 바꿔서 보내자
		
//		MailSender mailSender = Mockito.mock(MailSender.class);
//		Mockito.when(voMock.getNo2()).thenReturn(10L);
//		Long no = (Long)voMock.getNo2();
		
		ResultActions resultActions = mockMvc.perform(post("/api/guestbook/add").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vo)));

		resultActions
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")))
		.andExpect(jsonPath("$.data.name", is(vo.getName())))
		.andExpect(jsonPath("$.data.password", is(vo.getPassword())))
		.andExpect(jsonPath("$.data.contents", is(vo.getContents())));
		
	}
	
	
	

	@Test
	public void testDeleteGuestbook() throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", 3L);
		map.put("password", "1234");
		
		ResultActions resultActions = mockMvc.perform(delete("/api/guestbook/delete").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(map)));

		resultActions
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.result", is("success")));
		
	}
}
