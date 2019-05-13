package com.lxg.spring.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxg.spring.service.SUserService;

@Controller
//@ResponseBody //加上@ResponseBody之后，返回的结果将是一个json 。 如果不加将返回一个页面
//@RestController的作用就相当于@Controller+@ResponseBody
public class IndexController {

    @Resource
    private SUserService sUserService;

    @RequestMapping("/home")
    public String home() {
        return "home";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin")
    public String toAdmin(){
        return "helloAdmin";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";

    }
    
    @RequestMapping("/login")
    public String login(){
        return "pagelogin";
    }

    @RequestMapping("/")
    public String root() {
        return "index";

    }
    
    @RequestMapping("/indetables")
    public String indetables() {
        return "editable_table";

    }
    
    @RequestMapping("/pagesLogin")
    public String pagesLogin(){
        return "pagelogin";
    }
 

//    @RequestMapping("/index")
//    public String indexFile(){
//        return "index";
//    }
    
    @RequestMapping("/403")
    public String error(){
        return "403";
    }
    
    @RequestMapping("/user")
    public String uesr(){
        return "user";
    }
}
