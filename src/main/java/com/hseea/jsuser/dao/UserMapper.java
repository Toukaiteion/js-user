package com.hseea.jsuser.dao;

import com.hseea.jsuser.entity.PermissionDto;

import java.util.List;
import java.util.Set;

public interface UserMapper {
    List<PermissionDto> findMenuByUsername(Set<Integer> set);

    Set<Integer> getPermissionIdsByUsername(String username);
}
