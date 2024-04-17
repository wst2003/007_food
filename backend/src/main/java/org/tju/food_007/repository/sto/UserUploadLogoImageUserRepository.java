package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.UserEntity;

/**
 * @author WGY
 * @create 2024-04-17-14:52
 */
public interface UserUploadLogoImageUserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUserId(Integer user_id);
}
