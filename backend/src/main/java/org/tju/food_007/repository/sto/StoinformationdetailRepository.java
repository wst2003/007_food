package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreEntity;

public interface StoinformationdetailRepository extends JpaRepository<StoreEntity,Integer> {

    boolean existsByStoId(Integer id);
}
