package org.tju.food_007.repository.cus.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.UserEntity;

public interface GetUserInfoRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserId(int userId);
}
