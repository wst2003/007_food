package org.tju.food_007.service.cus.indent;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.com.SearchCommodityResponseDTO;
import org.tju.food_007.dto.cus.indent.GetIndRequestDTO;
import org.tju.food_007.dto.cus.indent.GetIndResponseDTO;
import org.tju.food_007.dto.cus.indent.mapper.GetIndResponseMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.cus.indent.GetIndComPositionRepository;
import org.tju.food_007.repository.cus.indent.GetIndCommodityRepository;
import org.tju.food_007.repository.cus.indent.GetIndentComRepository;
import org.tju.food_007.repository.cus.indent.GetIndentRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class GetIndentService {
    private final GetIndResponseMapper getIndResponseMapper =
            GetIndResponseMapper.INSTANCE;

    @Autowired
    GetIndCommodityRepository getIndCommodityRepository;

    @Autowired
    GetIndComPositionRepository getIndComPositionRepository;

    @Autowired
    GetIndentComRepository getIndentComRepository;

    @Autowired
    GetIndentRepository getIndentRepository;

    public List<GetIndResponseDTO> getIndbyUserID(GetIndRequestDTO request)
    {
        List<GetIndResponseDTO> responses =new ArrayList<>();

        List<IndentEntity>indents=getIndentRepository.findAllByCusId(request.getCus_ID());

        //筛选类别
        if(request.getState() != null){
            Iterator<IndentEntity> iterator = indents.iterator();
            while (iterator.hasNext()) {
                IndentEntity dto = iterator.next();
                if (!Objects.equals(dto.getIndState(), request.getState())) {
                    iterator.remove(); // 从结果中删除 sto_id 不匹配的项
                }
            }
        }

        for(IndentEntity indent : indents){
            GetIndResponseDTO tempDTO = new GetIndResponseDTO();

            List<GetIndResponseDTO.IndComDTO> tempComDTOs =new ArrayList<>();
            int sto_id=0;
            List<IndentCommodityEntity> IndComs = getIndentComRepository.findAllByIndId(indent.getIndId());
            for(IndentCommodityEntity IndCom :IndComs){
                GetIndResponseDTO.IndComDTO tempComDTO = new GetIndResponseDTO.IndComDTO();
                CommodityEntity commodity = getIndCommodityRepository.findByComId(IndCom.getComId());
                tempComDTO.setCom_ID(IndCom.getComId());
                tempComDTO.setInd_quantity(IndCom.getIndQuantity());
                tempComDTO.setCommodity_money(Double.parseDouble(IndCom.getCommodityMoney().toString()));
                tempComDTO.setRating_type(IndCom.getRatingType());
                tempComDTO.setCom_name(commodity.getComName());
                tempComDTOs.add(tempComDTO);
                sto_id=commodity.getStoId();
            }
            UserEntity store=getIndComPositionRepository.findByUserId(sto_id);
            tempDTO=getIndResponseMapper.entityToResponse(indent ,tempComDTOs.toArray(new GetIndResponseDTO.IndComDTO[0]),store);

            responses.add(tempDTO);

        }

        return responses;
    }


}
