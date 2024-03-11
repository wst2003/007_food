package org.tju.food_007.service.com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.com.CommodityImageResponseDTO;
import org.tju.food_007.dto.com.CommodityPriceCurveResponseDTO;
import org.tju.food_007.dto.com.SearchCommodityRequestDTO;
import org.tju.food_007.dto.com.SearchCommodityResponseDTO;
import org.tju.food_007.repository.com.CommoditySearchRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class CommoditySearchService {
    @Autowired
    CommoditySearchRepository commoditySearchRepository;

    /**
     * @author fjh
     * @date 2024/3/9
     * @Description 搜索商品
     */
    public List<SearchCommodityResponseDTO> searchCommodity(SearchCommodityRequestDTO request){
        List<SearchCommodityResponseDTO> response =new ArrayList<>();
        String sort_order;
        String sort_by = "";

        if(request.getSort_order() == 0){ sort_order="ASC";}//升序
        else{sort_order="DESC";}

        if(request.getSort_by()==0){sort_by="COM_ORIPRICE";}
        else if (request.getSort_by()==1) {sort_by="PRAISE_RATE";}
        else if (request.getSort_by()==2) {sort_by="COM_UPLOADDATE";}

        List<Object[]> objects=new ArrayList<>();

        if(request.getCom_type() ==null){
            System.out.println("无商品类别查询");
            objects=commoditySearchRepository.searchCommodity(request.getContent(),sort_by, sort_order);
        }
        else{
            objects=commoditySearchRepository.searchCommoditybyType
                    (request.getContent(),sort_by, sort_order,request.getCom_type());
        }

        for(Object[] tempObj : objects){
            SearchCommodityResponseDTO tempDTO = new SearchCommodityResponseDTO();
            if(tempObj[0]!=null)tempDTO.setCom_ID((Integer) tempObj[0]);
            if(tempObj[1]!=null)tempDTO.setCom_name(tempObj[1].toString());
            if(tempObj[6]!=null)tempDTO.setCom_left((Integer)tempObj[6]);
            if(tempObj[7]!=null)tempDTO.setPraise_rate(Double.parseDouble(tempObj[7].toString()));
            if(tempObj[8]!=null)tempDTO.setSto_ID((Integer)tempObj[8]);
            if(tempObj[15]!=null)tempDTO.setCom_position(tempObj[15].toString());
            if(tempObj[14]!=null){
                List<String> images = Arrays.stream(tempObj[14].toString().split(",")).toList();
                tempDTO.setCommodityImage(images.getFirst());
            }
            if(tempObj[16]!=null){
                List<String> curves =Arrays.stream(tempObj[16].toString().split(",")).toList();
                List<CommodityPriceCurveResponseDTO> commodityPriceCurveResponseDTOS =new ArrayList<>();
                for(String curve : curves){
                    String[] cur =Arrays.stream(curve.split("#")).toList().toArray(new String[0]);
                    CommodityPriceCurveResponseDTO commodityPriceCurveResponseDTO = new CommodityPriceCurveResponseDTO();
                    commodityPriceCurveResponseDTO.setCom_pc_time(cur[0]);
                    commodityPriceCurveResponseDTO.setCom_pc_price(Double.parseDouble(cur[1]));
                    commodityPriceCurveResponseDTOS.add(commodityPriceCurveResponseDTO);
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                CommodityPriceCurveResponseDTO maxDTO = null;
                LocalDateTime currentDateTime = LocalDateTime.now();

                for (CommodityPriceCurveResponseDTO dto : commodityPriceCurveResponseDTOS) {
                    LocalDateTime curveDateTime = LocalDateTime.parse(dto.getCom_pc_time(), formatter);

                    if (curveDateTime.isBefore(currentDateTime) || curveDateTime.isEqual(currentDateTime)) {
                        if (maxDTO == null || curveDateTime.isAfter(LocalDateTime.parse(maxDTO.getCom_pc_time(), formatter))) {
                            maxDTO = dto;
                        }
                    }
                }
                tempDTO.setCom_price(maxDTO.getCom_pc_price());
            }
            response.add(tempDTO);
        }

        if(request.getSort_by()==0 && request.getSort_order()==0){
            response.sort(new Comparator<SearchCommodityResponseDTO>() {
                @Override
                public int compare(SearchCommodityResponseDTO dto1, SearchCommodityResponseDTO dto2) {
                    // 比较商品价格，如果价格相同则不改变顺序
                    return Double.compare(dto1.getCom_price(), dto2.getCom_price());
                }
            });
        }

        if(request.getSort_by()==0 && request.getSort_order()==1){
            response.sort(new Comparator<SearchCommodityResponseDTO>() {
                @Override
                public int compare(SearchCommodityResponseDTO dto1, SearchCommodityResponseDTO dto2) {
                    // 比较商品价格，如果价格相同则不改变顺序
                    return Double.compare(dto2.getCom_price(), dto1.getCom_price());
                }
            });
        }

        if(request.getSto_ID() != null){
            Iterator<SearchCommodityResponseDTO> iterator = response.iterator();
            while (iterator.hasNext()) {
                SearchCommodityResponseDTO dto = iterator.next();
                if (!Objects.equals(dto.getSto_ID(), request.getSto_ID())) {
                    iterator.remove(); // 从结果中删除 sto_id 不匹配的项
                }
            }
        }




        int page_size = request.getPage_size();
        int page_num = request.getPage_num();
        System.out.println("查询到总数为" + response.size());
        if (response.size() <= page_size * (page_num - 1)) {
            response.clear();
            return response; // 如果请求页超出范围，则返回空列表
        }
        int beg = page_size * (page_num - 1); // 起始索引
        int end = Math.min(page_size * page_num, response.size()); // 结束索引（不包括）

        System.out.println("返回总数为" + (end - beg));

        return response.subList(beg, end);

    }


}
