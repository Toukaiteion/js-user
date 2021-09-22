package com.hseea.jsuser.entity;

import lombok.Data;

@Data
public class PermissionDto {
    private int id;
    private String resourceName;
    private String resourceNameZh;
    private String resourceDesc;
    private String url;
    private String component;
    private int priority;
    private int parentId;
    private int resourceType;
}
