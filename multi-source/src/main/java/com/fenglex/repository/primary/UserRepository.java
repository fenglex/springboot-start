package com.fenglex.repository.primary;

import com.fenglex.entity.primary.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select distinct(u.createTime) from User u order by  u.createTime desc")
    List<Long> findAllCreateTime();

    @Transactional
    void deleteAllByCreateTimeIsLessThanEqual(int start);
}
