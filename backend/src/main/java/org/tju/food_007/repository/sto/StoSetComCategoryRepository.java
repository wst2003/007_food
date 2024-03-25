package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommodityCategoriesEntity;

public interface StoSetComCategoryRepository extends JpaRepository<CommodityCategoriesEntity,Integer> {
}
