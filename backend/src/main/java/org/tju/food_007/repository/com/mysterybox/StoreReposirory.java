package org.tju.food_007.repository.com.mysterybox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreEntity;

public interface StoreReposirory extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findFirstByStoId(int sto_ID);
}
