package org.tju.food_007.repository.com.mysterybox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityImageEntity;
import org.tju.food_007.model.CommodityImageEntityPK;

import java.util.List;

public interface CommodityImageRepository extends JpaRepository<CommodityImageEntity, CommodityImageEntityPK> {
    List<CommodityImageEntity> findAllByComId(int com_id);
}
