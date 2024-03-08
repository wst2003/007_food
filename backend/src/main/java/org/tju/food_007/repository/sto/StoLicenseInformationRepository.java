package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreLicenseEntity;

import java.util.List;

public interface StoLicenseInformationRepository extends JpaRepository<StoreLicenseEntity,Integer> {

    List<StoreLicenseEntity> findAllByStoId(Integer id);

}
