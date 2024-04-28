package org.tju.food_007.repository.com.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityEntity;

import java.util.List;

public interface RecommendCommodityRepository extends JpaRepository<CommodityEntity,Integer> {
   List<CommodityEntity> findAll();

    boolean existsByComId(Integer Com_ID);
}