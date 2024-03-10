package org.tju.food_007.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CustomerEntity;

/**
 * @author WGY
 * @create 2024-03-06-14:31
 */
public interface CustomRegistrationRepository extends JpaRepository<CustomerEntity,Integer> {
}
