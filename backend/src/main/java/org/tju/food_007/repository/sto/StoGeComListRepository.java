package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WGY
 * @create 2024-04-01-18:53
 */
public interface StoGeComListRepository extends JpaRepository<CommodityEntity,Integer> {
    ArrayList<CommodityEntity> findCommodityEntitiesByStoId(Integer sto_id);
}
