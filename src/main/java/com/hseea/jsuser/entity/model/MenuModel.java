package com.hseea.jsuser.entity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuModel implements Serializable {
    private int id;
    private String menuName;
    private String url;
    private int priority;
    private List<MenuModel> subMenu;
}
