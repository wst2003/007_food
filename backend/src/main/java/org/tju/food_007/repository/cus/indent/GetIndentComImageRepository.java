package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityImageEntity;
import org.tju.food_007.model.CommodityImageEntityPK;

public interface GetIndentComImageRepository extends JpaRepository<CommodityImageEntity, CommodityImageEntityPK> {
    CommodityImageEntity findFirstByComId(int com_ID);

}
