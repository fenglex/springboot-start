package com.fenglex.aop.cache.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserInfo implements Serializable {
    private String home;
    private String address;
    List<Password> passwords;
    Map<String, Password> map;
}
