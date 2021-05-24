package com.lyun.smartalbums.controller;

import com.lyun.smartalbums.utils.HomePathUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetLocation {
    @GetMapping("/getLocation")
    public String getLocation(){
        return "theLocation:"+ HomePathUtils.getPath();
    }
}
