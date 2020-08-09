package com.fh.shop.api.classify.controller;

import com.fh.shop.api.classify.biz.IClassifyService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/classify")
public class ClassifyController {

    @Resource(name = "classifyService")
    private IClassifyService classifyService;

    @GetMapping
    public ServerResponse queryClassifyAll(){

        return classifyService.queryClassifyAll();
    }
}
