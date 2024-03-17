package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

/**
 * @author WGY
 * @create 2024-03-17-21:26
 */
public interface IndentCommentDetailRepository extends JpaRepository<CommodityEntity,Integer> {
    CommodityEntity findByComId(Integer com_id);
}
