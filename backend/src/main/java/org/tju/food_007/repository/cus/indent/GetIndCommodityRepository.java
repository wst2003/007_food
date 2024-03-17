package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

public interface GetIndCommodityRepository extends JpaRepository<CommodityEntity,Integer> {
    CommodityEntity findByComId(int com_ID);
}
