package com;

import com.fenglex.Application;
import com.fenglex.entity.primary.User;
import com.fenglex.entity.secondary.UserInfo;
import com.fenglex.repository.primary.UserRepository;
import com.fenglex.repository.secondary.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MultiSourceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void test() {
        User user = new User();
        user.setUsername("fenglex");
        user.setPassword("123456");
        int time = (int) (System.currentTimeMillis() / 1000);
        user.setCreateTime(time);
        user.setUpdateTime(time);
        userRepository.save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("fenglex");
        userInfo.setAddress("北京");
        userInfo.setCreateTime(time);
        userInfo.setUpdateTime(time);
        userInfoRepository.save(userInfo);
    }
}
