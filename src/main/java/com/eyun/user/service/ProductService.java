package com.eyun.user.service;

import com.eyun.user.client.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Jack_wen
 * @date 2018/4/18
 */
@AuthorizedFeignClient(name="product")
public interface ProductService {

    @GetMapping("/api/product/shop")
    List<Map> ProductList(@RequestParam("shopId")Long id,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize);

}
