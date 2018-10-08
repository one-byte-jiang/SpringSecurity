package com.example.demo;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码加密规则
 * */
public class MyPasswordEncoder implements PasswordEncoder{
    //对一个原始的密码进行加密
    @Override
    public String encode(CharSequence charSequence) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(charSequence.toString(),"123456");
    }
    //原始的密码和加密后的密码进行匹配
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.isPasswordValid(s,charSequence.toString(),"123456");
    }
}
