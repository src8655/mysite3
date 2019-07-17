package com.cafe24.mysite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe24.mysite.vo.GuestbookVo;

@Service
public class GuestbookService2 {

	public List<GuestbookVo> getContentsList(int no) {

		GuestbookVo first = new GuestbookVo(1L, "user1", "password1", "contents1", "regDate1");
		GuestbookVo second = new GuestbookVo(2L, "user2", "password2", "contents2", "regDate2");
		
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		list.add(first);
		list.add(second);
		
		return list;
	}

	public GuestbookVo addContents(GuestbookVo guestbookVo) {
		guestbookVo.setNo(3L);
		guestbookVo.setRegDate("2019-07-10");
		return guestbookVo;
	}

	public Long deleteContents(Long no, String password) {
		
		return no;
	}

}
