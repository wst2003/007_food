package org.tju.food_007.repository.com.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.IndentEntity;

public interface RecommendIndentRepositiry extends JpaRepository<IndentEntity,Integer> {
    IndentEntity findFirstByCusId(Integer cus_ID);
}
