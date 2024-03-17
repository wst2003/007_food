package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.IndentEntity;

import java.util.List;

public interface GetIndentRepository extends JpaRepository<IndentEntity,Integer> {
    List<IndentEntity> findAllByCusId(int cus_id);
}
