package com.fenglex.aop.cache.service.impl;

import com.fenglex.aop.cache.component.Cache;
import com.fenglex.aop.cache.model.Password;
import com.fenglex.aop.cache.model.User;
import com.fenglex.aop.cache.model.UserInfo;
import com.fenglex.aop.cache.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheServiceImpl implements CacheService {


    @Override
    //
    // @Cache(key = "#up.name")
    @Cache
    public User getUserInfo(User up) {
        User user = new User();
        user.setAge("12");
        user.setName("haifeng");
        UserInfo userInfo = new UserInfo();
        userInfo.setAddress("henan");
        userInfo.setHome("beijing");
        user.setUserInfo(userInfo);

        List<UserInfo> userList = new ArrayList<>();
        userList.add(userInfo);
        userList.add(userInfo);
        user.setList(userList);

        Map<String, Password> map = new HashMap<>();
        Password password = new Password();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        password.setList(list);
        Map<String, String> strMap = new HashMap<>();
        for (String s : list) {
            strMap.put(s, s);
        }
        password.setList(list);
        password.setMap(strMap);
        map.put("u", password);
        user.setMap(map);
        return user;
    }
}
