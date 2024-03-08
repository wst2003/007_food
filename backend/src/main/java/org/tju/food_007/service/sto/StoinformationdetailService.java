package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.sto.StoinformationdetailResponseDTO;
import org.tju.food_007.dto.sto.mapper.StoinformationdetailMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.sto.*;

import java.util.Optional;

@Service
public class StoinformationdetailService {
    @Autowired
    UserInformatonDetailRepository userInformatonDetailRepository;

    @Autowired
    StoinformationdetailRepository stoinformationdetailRepository;

    @Autowired
    StoCategoriesInformationRepository stoCategoriesInformationRepository;

    @Autowired
    StoImageInformationRepository stoImageInformationRepository;

    @Autowired
    StoLicenseInformationRepository stoLicenseInformationRepository;

    private final StoinformationdetailMapper stoinformationdetailMapper =
            StoinformationdetailMapper.INSTANCE;

    public StoinformationdetailResponseDTO stoinformationdetail(Integer id){
        StoinformationdetailResponseDTO response = new StoinformationdetailResponseDTO();

        boolean existUser =userInformatonDetailRepository.existsByUserId(id);
        boolean existSto =stoinformationdetailRepository.existsByStoId(id);

        if(!existSto || !existUser){
            response.setUser_ID(-1);
            return response;
        }

        Optional<UserEntity> user = userInformatonDetailRepository.findById(id);
        Optional<StoreEntity> store = stoinformationdetailRepository.findById(id);
        StoreCategoriesEntity[] storeCategoriesEntities =
                stoCategoriesInformationRepository.findAllByStoreId(id).toArray(new StoreCategoriesEntity[0]);
        StoreImageEntity[] storeImageEntities =
                stoImageInformationRepository.findAllByStoId(id).toArray(new StoreImageEntity[0]);
        StoreLicenseEntity[] storeLicenseEntities =
                stoLicenseInformationRepository.findAllByStoId(id).toArray(new StoreLicenseEntity[0]);

        response = stoinformationdetailMapper.entityToResponse
                (user.orElseThrow(),store.orElseThrow(),storeCategoriesEntities,storeImageEntities,storeLicenseEntities);



        return response;

    }




















}
