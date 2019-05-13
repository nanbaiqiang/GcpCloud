package com.lxg.spring.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lxg.spring.dao.SRoleRepository;
import com.lxg.spring.entity.SRole;
import com.lxg.spring.entity.SUser;
import com.lxg.spring.entity.SecurityUser;
import com.lxg.spring.service.SUserService;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //数据库服务类
    private SUserService suserService;//code7
    
    @Autowired  //数据库服务类
    private SRoleRepository sroleService;//code7

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //SUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        //本例使用SUser中的email作为用户名:
        SUser user = suserService.findUserByEmail(userName); //code8

        if (user == null) {

            throw new UsernameNotFoundException("UserName " + userName + " not found");

        }

        //List<SRole> roles= sroleService.findByUid(String.valueOf(user.getId()));
        
        // SecurityUser实现UserDetails并将SUser的Email映射为username
        SecurityUser securityUser = new SecurityUser(user);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        
        for (SRole role : user.getSRoles()) 
        {
        	 authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        
        return securityUser; //code9
        //return new User(user.getName(), user.getPassword(), authorities);

    }

}
