package com.fenglex.entity.secondary;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_user_info")
@ToString
@NoArgsConstructor
public class UserInfo {
    @Id  //ID代表是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String address;
    private int createTime;
    private int updateTime;
}
