package com.eyun.user.service;

import org.springframework.web.bind.annotation.GetMapping;
import com.eyun.user.client.AuthorizedFeignClient;
import com.eyun.user.service.dto.UserDTO;


@AuthorizedFeignClient(name="uaa")
public interface UaaService {

	@GetMapping("/api/account")
	public UserDTO getAccount();
	
}
