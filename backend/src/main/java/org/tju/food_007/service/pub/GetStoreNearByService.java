package org.tju.food_007.service.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.GetStoreNearByRequestDTO;
import org.tju.food_007.dto.pub.GetStoreNearByResponseDTO;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.repository.pub.GetStoreNearByRepository;
import org.tju.food_007.utils.GeoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WGY
 * @create 2024-03-10-15:07
 */
@Service
public class GetStoreNearByService {
    @Autowired
    GetStoreNearByRepository getStoreNearByRepository;
    @Autowired
    GeoService geoService;
    @Transactional
    public GetStoreNearByResponseDTO GetStoNearBy(GetStoreNearByRequestDTO request)
    {
        List<StoreEntity>allStoreList=getStoreNearByRepository.findAll();
        ArrayList<Integer> targetStoId= new ArrayList<Integer>();
        GetStoreNearByResponseDTO response=new GetStoreNearByResponseDTO();
        for(StoreEntity sto :allStoreList){
            if(geoService.calculateDistanceInKilometer(request.getCur_latitude(),
                    request.getCur_longitude(),Double.parseDouble(sto.getStoLatitude()),
                    Double.parseDouble(sto.getStoLongitude()))<=5)
            {
                targetStoId.add(sto.getStoId());
            }
        }
        response.setSto_id(targetStoId);

        return  response;
    }
}
