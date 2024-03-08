package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityCategoriesEntity;
import org.tju.food_007.model.CommodityCategoriesEntityPK;

import java.util.List;

public interface CommodityCategoriesRepository extends JpaRepository<CommodityCategoriesEntity, CommodityCategoriesEntityPK> {
    List<CommodityCategoriesEntity> findAllByComIdIs(Integer id);

}
