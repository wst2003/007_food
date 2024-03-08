package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreImageEntity;

import java.util.List;

public interface StoImageInformationRepository extends JpaRepository<StoreImageEntity,Integer> {
    List<StoreImageEntity> findAllByStoId(Integer ID);

}
