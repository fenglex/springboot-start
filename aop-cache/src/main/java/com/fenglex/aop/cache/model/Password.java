package com.fenglex.aop.cache.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class Password implements Serializable {
    List<String> list;
    Map<String, String> map;
}
