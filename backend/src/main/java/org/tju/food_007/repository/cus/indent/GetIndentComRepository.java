package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentCommodityEntityPK;

import java.util.List;

public interface GetIndentComRepository extends JpaRepository<IndentCommodityEntity, IndentCommodityEntityPK> {
    List<IndentCommodityEntity> findAllByIndId(int ind_ID);
}
