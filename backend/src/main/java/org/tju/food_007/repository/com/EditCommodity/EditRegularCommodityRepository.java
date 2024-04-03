package org.tju.food_007.repository.com.EditCommodity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.RegularCommodityEntity;

public interface EditRegularCommodityRepository extends JpaRepository<RegularCommodityEntity,Integer> {
    RegularCommodityEntity findByRegularComId(int com_ID);
}
