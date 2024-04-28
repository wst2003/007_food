package org.tju.food_007.repository.com.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentCommodityEntityPK;

public interface RecommendIndentDetailRepository extends JpaRepository<IndentCommodityEntity, IndentCommodityEntityPK> {
    IndentCommodityEntity findFirstByIndId(Integer int_ID);
}
