package com.hseea.jsuser.entity.model;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private int id;
    private int userId;
    private String username;
    private List<MenuModel> menu;
}
