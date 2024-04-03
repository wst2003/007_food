package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreEntity;

public interface GetIndStoreRespository extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findByStoId(int sto_ID);
}
