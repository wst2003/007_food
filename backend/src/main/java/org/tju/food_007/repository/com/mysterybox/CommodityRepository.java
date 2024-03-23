package org.tju.food_007.repository.com.mysterybox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

import java.util.List;

public interface CommodityRepository extends JpaRepository<CommodityEntity,Integer> {
    List<CommodityEntity> findAllByStoIdAndComType(int sto_ID , int com_type);

    List<CommodityEntity> findAllByComType(int com_type);

    CommodityEntity findFirstByComId(int com_ID);
}
