package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

/**
 * @author WGY
 * @create 2024-03-20-13:52
 */
public interface StoUploadCommodityRepository extends JpaRepository<CommodityEntity,Integer> {
}
