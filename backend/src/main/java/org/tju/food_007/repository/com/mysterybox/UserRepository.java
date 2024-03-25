package org.tju.food_007.repository.com.mysterybox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findFirstByUserId(int user_ID);
}
