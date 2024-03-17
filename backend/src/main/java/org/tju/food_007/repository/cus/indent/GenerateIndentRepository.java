package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.IndentEntity;

/**
 * @author WGY
 * @create 2024-03-17-15:41
 */
public  interface GenerateIndentRepository extends JpaRepository<IndentEntity,Integer> {
    IndentEntity findByIndId(Integer ind_id);
}
