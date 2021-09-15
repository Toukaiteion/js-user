package com.hseea.jsuser.entity;

import lombok.Data;

@Data
public class UserMenuDto {
    private int id;
    private int userId;
    private String username;
    private String menuName;
    private String url;
    private int priority;
    private int parentId;
}
