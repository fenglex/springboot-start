import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.fenglex.aop.cache.model.Password;
import com.fenglex.aop.cache.model.User;
import com.fenglex.aop.cache.model.UserInfo;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTest {
    public static void main(String[] args) {
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

        Field[] fields = ReflectUtil.getFields(user.getClass());

        for (Field field : fields) {
            String name = field.getName();
            Object fieldValue = ReflectUtil.getFieldValue(user, field);
            System.out.println(name + "--->" + JSON.toJSONString(fieldValue));
        }

        System.out.println(user.toString());
    }
}
