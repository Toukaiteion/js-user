package com.hseea.jsuser.service.impl;

import com.hseea.jsuser.dao.UserMapper;
import com.hseea.jsuser.entity.UserMenuDto;
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
        List<UserMenuDto> userMenuDto = userMapper.findMenuByUsername(username);
        userInfo.setUsername(username);
        Map<Integer, MenuModel> mainMenuModelMap = new TreeMap<>();
        Map<Integer, List<MenuModel>> subMenuModelMap = new TreeMap<>();
        userMenuDto.forEach(dto -> {
            MenuModel menuModel = new MenuModel();
            menuModel.setMenuName(dto.getMenuName());
            menuModel.setId(dto.getId());
            menuModel.setUrl(dto.getUrl());
            if(dto.getParentId()==0){
                mainMenuModelMap.put(dto.getId(),menuModel);
            }else {
                List<MenuModel> menuModelList = ObjectUtils.isEmpty(subMenuModelMap.get(dto.getParentId()))?
                        new ArrayList<>():
                        subMenuModelMap.get(dto.getParentId());
                menuModelList.add(menuModel);
                menuModelList.sort(Comparator.comparingInt(MenuModel::getPriority));
                subMenuModelMap.put(dto.getParentId(),menuModelList);
            }
        });
        for(Integer parentId : subMenuModelMap.keySet()){
            mainMenuModelMap.get(parentId).setSubMenu(subMenuModelMap.get(parentId));
        }
        List<MenuModel> list = new ArrayList<>(mainMenuModelMap.values());
        list.sort(Comparator.comparingInt(MenuModel::getPriority));
        userInfo.setMenu(list);
        return userInfo;
    }
}
