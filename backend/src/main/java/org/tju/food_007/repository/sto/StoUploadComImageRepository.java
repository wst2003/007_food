package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityImageEntity;

/**
 * @author WGY
 * @create 2024-03-24-19:30
 */
public interface StoUploadComImageRepository extends JpaRepository<CommodityImageEntity,Integer> {
}
