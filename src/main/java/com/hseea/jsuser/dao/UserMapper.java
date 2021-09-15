package com.hseea.jsuser.dao;

import com.hseea.jsuser.entity.UserMenuDto;

import java.util.List;

public interface UserMapper {
    List<UserMenuDto> findMenuByUsername(String username);
}
