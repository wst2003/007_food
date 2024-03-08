package org.tju.food_007.repository.sto;


import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreCategoriesEntity;
import org.tju.food_007.model.StoreCategoriesEntityPK;

import java.util.List;

public interface StoCategoriesInformationRepository extends JpaRepository<StoreCategoriesEntity, StoreCategoriesEntityPK> {
    List<StoreCategoriesEntity> findAllByStoreId(Integer id);

}
