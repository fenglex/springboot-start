package com.fenglex.aop.cache.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class User {
    private String name;
    private String age;
    private UserInfo userInfo;
    List<UserInfo> list;
    Map<String, Password> map;
}
