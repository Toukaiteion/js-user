package com.hseea.jsuser.service.impl;

import com.hseea.jsuser.constant.Constant;
import com.hseea.jsuser.dao.UserMapper;
import com.hseea.jsuser.entity.PermissionDto;
import com.hseea.jsuser.entity.model.MenuModel;
import com.hseea.jsuser.entity.model.UserInfo;
import com.hseea.jsuser.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserInfo getMenu(String username) {
        UserInfo userInfo = new UserInfo();
        Set<Integer> permissionIds = userMapper.getPermissionIdsByUsername(username);
        List<PermissionDto> permissionDtoList = userMapper.findMenuByUsername(permissionIds);
        userInfo.setUsername(username);
        Map<Integer, MenuModel> permissionMap = new HashMap<>();
        permissionDtoList.forEach(dto -> {
            MenuModel menuModel = new MenuModel();
            menuModel.setMenuName(dto.getResourceNameZh());
            menuModel.setId(dto.getId());
            menuModel.setUrl(dto.getUrl());
            menuModel.setComponent(dto.getComponent());
            menuModel.setPriority(dto.getPriority());
            permissionMap.put(dto.getId(), menuModel);
        });
        List<MenuModel> list = new ArrayList<>();
        permissionDtoList.forEach(dto -> {
            MenuModel tempMenuModel = permissionMap.get(dto.getId());
            if(dto.getParentId()==Constant.MENU_ROOT){
                list.add(tempMenuModel);
            }else {
                if(dto.getResourceType() == Constant.RESOURCE_TYPE_WEB){
                    List<MenuModel> subMenuList = ObjectUtils.isEmpty(permissionMap.get(dto.getParentId()).getSubMenu())?
                            new ArrayList<>():permissionMap.get(dto.getParentId()).getSubMenu();
                    subMenuList.add(tempMenuModel);
                    permissionMap.get(dto.getParentId()).setSubMenu(subMenuList);
                }else {
                    List<MenuModel> subElementList = ObjectUtils.isEmpty(permissionMap.get(dto.getParentId()).getSubElement())?
                            new ArrayList<>():permissionMap.get(dto.getParentId()).getSubElement();
                    subElementList.add(tempMenuModel);
                    permissionMap.get(dto.getParentId()).setSubElement(subElementList);
                }
            }
            }
        );
        for(MenuModel menuModel : list){
            if(!ObjectUtils.isEmpty(menuModel.getSubMenu())){
                menuModel.getSubMenu().sort(Comparator.comparingInt(MenuModel::getPriority));
            }
        }
        list.sort(Comparator.comparingInt(MenuModel::getPriority));
        userInfo.setMenu(list);
        return userInfo;
    }
}
