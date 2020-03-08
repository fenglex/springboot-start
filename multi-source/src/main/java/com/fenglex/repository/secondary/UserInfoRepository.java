package com.fenglex.repository.secondary;

import com.fenglex.entity.secondary.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {

}
