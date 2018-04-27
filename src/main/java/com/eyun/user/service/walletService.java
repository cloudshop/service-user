package com.eyun.user.service;

import com.eyun.user.client.AuthorizedFeignClient;
import com.eyun.user.service.dto.ServiceProviderRewardDTO;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Created by Jack_wen on 2018/4/27.
 */
@AuthorizedFeignClient(name="wallet")
public interface walletService {

    @PutMapping("/api/serviceProvider/reward")
    void invitationDeductions( ServiceProviderRewardDTO serviceProviderRewardDTO);
}