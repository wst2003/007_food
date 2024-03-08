package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

import java.util.List;

public interface CommodityInformationRepository extends JpaRepository<CommodityEntity,Integer> {

    List<CommodityEntity> findAllByStoId(Integer id);

}
