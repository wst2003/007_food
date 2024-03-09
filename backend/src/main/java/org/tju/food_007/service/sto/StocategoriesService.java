package org.tju.food_007.service.sto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.sto.StoCategoriesOnsaleResponseDTO;
import org.tju.food_007.dto.sto.mapper.StoCategoriesOnsaleMapper;
import org.tju.food_007.model.CommodityCategoriesEntity;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.repository.sto.CommodityCategoriesRepository;
import org.tju.food_007.repository.sto.CommodityInformationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StocategoriesService {
    @Autowired
    CommodityInformationRepository commodityInformationRepository;

    @Autowired
    CommodityCategoriesRepository commodityCategoriesRepository;

    private final StoCategoriesOnsaleMapper stoCategoriesOnsaleMapper =
            StoCategoriesOnsaleMapper.INSTANCE;

    /**
     * @author fjh
     * @date 2024/3/8
     * @Description 获取商家在售商品
     */
    public StoCategoriesOnsaleResponseDTO getStoCategoriesOnsale(Integer sto_Id){
        CommodityEntity[] commodityEntities =
                commodityInformationRepository.findAllByStoId(sto_Id).toArray(new CommodityEntity[0]);
        StoCategoriesOnsaleResponseDTO response = new StoCategoriesOnsaleResponseDTO();
        if(commodityEntities.length==0){

            return response;
        }
        List<CommodityCategoriesEntity> commodityCategoriesEntities = new ArrayList<CommodityCategoriesEntity>();
        for(CommodityEntity commodity :commodityEntities){
            CommodityCategoriesEntity[] commodityCategoriesEntities1 =
                    commodityCategoriesRepository.findAllByComIdIs(commodity.getComId())
                            .toArray(new CommodityCategoriesEntity[0]);
            for(CommodityCategoriesEntity commodityCategoriesEntity : commodityCategoriesEntities1)
                commodityCategoriesEntities.add(commodityCategoriesEntity);

        }
        response = stoCategoriesOnsaleMapper.mapStoCat
                (commodityCategoriesEntities);


        return response;


    }



}
