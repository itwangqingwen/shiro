package com.immoc.controller;

import com.immoc.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @RequestMapping(value="/subLogin",method = RequestMethod.POST , produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){
      /*  System.out.println(User.getUsername());*/
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        if(subject.hasRole("admin")){
                return "admin";
        }
        return "null admin";

    }
    @RequiresRoles("admin")
    @RequiresPermissions("user:add")
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return  "testRole success";
    }
    @RequiresPermissions("user:add")
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return  "testRole1 success";
    }
}
