package com.fenglex.entity.primary;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_user")
@ToString
@NoArgsConstructor
public class User {
    @Id  //ID代表是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private int createTime;
    private int updateTime;
}
