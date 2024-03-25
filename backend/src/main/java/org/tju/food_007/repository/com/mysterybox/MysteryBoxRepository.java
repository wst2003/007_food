package org.tju.food_007.repository.com.mysterybox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.MysteryBoxEntity;

import java.util.List;

public interface MysteryBoxRepository extends JpaRepository<MysteryBoxEntity,Integer> {
    MysteryBoxEntity findFirstByMysteryBoxId(int id);

    List<MysteryBoxEntity> findAll();
}
