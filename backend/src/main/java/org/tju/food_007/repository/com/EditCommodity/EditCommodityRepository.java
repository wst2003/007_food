package org.tju.food_007.repository.com.EditCommodity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

public interface EditCommodityRepository extends JpaRepository<CommodityEntity,Integer> {
    boolean existsByComId(int com_ID);

    CommodityEntity findByComId(int com_ID);
}
