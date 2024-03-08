package org.tju.food_007.repository.sto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.StoreLicenseEntity;
import org.tju.food_007.model.StoreLicenseEntityPK;

import java.util.List;

public interface StoLicenseInformationRepository extends JpaRepository<StoreLicenseEntity, StoreLicenseEntityPK> {

    List<StoreLicenseEntity> findAllByStoId(Integer id);

}
