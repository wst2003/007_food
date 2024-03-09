package org.tju.food_007.service.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.com.*;
import org.tju.food_007.repository.com.CommodityDetailRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommodityInfomationService {

    @Autowired
    CommodityDetailRepository commodityDetailRepository;


    /**
     * @author fjh
     * @date 2024/3/9
     * @Description 查询单个商品详情
     */
    public CommodityDetailDTO getCommodityDetail(Integer com_id){
        CommodityDetailDTO commodityDetailDTO = new CommodityDetailDTO();

        List<Object[]> objects = commodityDetailRepository.getCommodityDetail(com_id);

        if(objects.isEmpty()){
            commodityDetailDTO.setCom_ID(-1);
            return commodityDetailDTO;
        }

        if(objects.getFirst()[0]!=null)commodityDetailDTO.setCom_ID((Integer) objects.getFirst()[0]);
        if(objects.getFirst()[1]!=null)commodityDetailDTO.setCom_name(objects.getFirst()[1].toString());
        if(objects.getFirst()[2]!=null)commodityDetailDTO.setCom_introduction(objects.getFirst()[2].toString());
        if(objects.getFirst()[3]!=null)commodityDetailDTO.setCom_oriPrice(Double.parseDouble(objects.getFirst()[3].toString()));
        if(objects.getFirst()[4]!=null)commodityDetailDTO.setCom_type((Integer) objects.getFirst()[4]);
        if(objects.getFirst()[5]!=null)commodityDetailDTO.setCom_uploadDate(objects.getFirst()[5].toString());
        if(objects.getFirst()[6]!=null)commodityDetailDTO.setCom_left((Integer)objects.getFirst()[6]);
        if(objects.getFirst()[7]!=null)commodityDetailDTO.setPraise_rate(Double.parseDouble(objects.getFirst()[7].toString()));
        if(objects.getFirst()[8]!=null)commodityDetailDTO.setSto_ID((Integer)objects.getFirst()[8]);
        if(objects.getFirst()[10]!=null)commodityDetailDTO.setSto_openingTime(objects.getFirst()[10].toString());
        if(objects.getFirst()[11]!=null)commodityDetailDTO.setSto_closingTime(objects.getFirst()[11].toString());
        if(objects.getFirst()[12]!=null)commodityDetailDTO.setCom_expirationDate(objects.getFirst()[12].toString());
        if(objects.getFirst()[13]!=null){
            List<String> categories = Arrays.stream(objects.getFirst()[13].toString().split(",")).toList();
            List<CommodityCategoriesResponseDTO> categoriesResponseDTOS =new ArrayList<>();
            for(String category : categories){
                CommodityCategoriesResponseDTO commodityCategoriesResponseDTO =new CommodityCategoriesResponseDTO();
                commodityCategoriesResponseDTO.setCom_category(category);
                categoriesResponseDTOS.add(commodityCategoriesResponseDTO);
            }
            commodityDetailDTO.setCommodity_categories(categoriesResponseDTOS.toArray(new CommodityCategoriesResponseDTO[0]));
        }
        if(objects.getFirst()[14]!=null){
            List<String> images = Arrays.stream(objects.getFirst()[14].toString().split(",")).toList();
            List<CommodityImageResponseDTO> commodityImageResponseDTOS =new ArrayList<>();
            for(String image : images){
                CommodityImageResponseDTO commodityImageResponseDTO =new CommodityImageResponseDTO();
                commodityImageResponseDTO.setCom_image(image);
                commodityImageResponseDTOS.add(commodityImageResponseDTO);
            }
            commodityDetailDTO.setCommodityImage(commodityImageResponseDTOS.toArray(new CommodityImageResponseDTO[0]));

        }
        List<CommodityPriceCurveResponseDTO> commodityPriceCurveResponseDTOS = new ArrayList<>();
        for(Object[] tempObj : objects){
            CommodityPriceCurveResponseDTO tempDTO = new CommodityPriceCurveResponseDTO();
            tempDTO.setCom_pc_time(tempObj[15].toString());
            tempDTO.setCom_pc_price(Double.parseDouble(tempObj[16].toString()));
            commodityPriceCurveResponseDTOS.add(tempDTO);
        }
        commodityDetailDTO.setCommodityPriceCurve(commodityPriceCurveResponseDTOS.toArray(new CommodityPriceCurveResponseDTO[0]));


        return commodityDetailDTO;



    }


    /**
     * @author fjh
     * @date 2024/3/9
     * @Description 查询商家的商品列表
     */
    public List<CommodityDetailDTO> getCommodityList(GetCommodityListRequestDTO request){
        List<CommodityDetailDTO> response =new ArrayList<>();

        List<Object[]> objects;
        if(request.getCom_type() ==null){
            System.out.println("无商品类别查询");
            objects=commodityDetailRepository.getCommodityList(request.getSto_ID());
        }
        else {
            objects=commodityDetailRepository.getCommodityListByCat(request.getSto_ID(),request.getCom_type());
        }
        System.out.println("查询到总数objects为"+objects.size());
        for (Object[] objArray : objects) {
            for (Object obj : objArray) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        for(Object[] tempObj : objects){
            CommodityDetailDTO tempDetailDTO = new CommodityDetailDTO();
            if(tempObj[0]!=null)tempDetailDTO.setCom_ID((Integer) tempObj[0]);
            if(tempObj[1]!=null)tempDetailDTO.setCom_name(tempObj[1].toString());
            if(tempObj[2]!=null)tempDetailDTO.setCom_introduction(tempObj[2].toString());
            if(tempObj[3]!=null)tempDetailDTO.setCom_oriPrice(Double.parseDouble(tempObj[3].toString()));
            if(tempObj[4]!=null)tempDetailDTO.setCom_type((Integer)tempObj[4]);
            if(tempObj[5]!=null)tempDetailDTO.setCom_uploadDate(tempObj[5].toString());
            if(tempObj[6]!=null)tempDetailDTO.setCom_left((Integer)tempObj[6]);
            if(tempObj[7]!=null)tempDetailDTO.setPraise_rate(Double.parseDouble(tempObj[7].toString()));
            if(tempObj[8]!=null)tempDetailDTO.setSto_ID((Integer)tempObj[8]);
            if(tempObj[10]!=null)tempDetailDTO.setSto_openingTime(tempObj[10].toString());
            if(tempObj[11]!=null)tempDetailDTO.setSto_closingTime(tempObj[11].toString());
            if(tempObj[12]!=null)tempDetailDTO.setCom_expirationDate(tempObj[12].toString());
            if(tempObj[13]!=null){
                List<String> categories = Arrays.stream(tempObj[13].toString().split(",")).toList();
                List<CommodityCategoriesResponseDTO> categoriesResponseDTOS =new ArrayList<>();
                for(String category : categories){
                    CommodityCategoriesResponseDTO commodityCategoriesResponseDTO =new CommodityCategoriesResponseDTO();
                    commodityCategoriesResponseDTO.setCom_category(category);
                    categoriesResponseDTOS.add(commodityCategoriesResponseDTO);
                }
                tempDetailDTO.setCommodity_categories(categoriesResponseDTOS.toArray(new CommodityCategoriesResponseDTO[0]));
            }
            if(tempObj[14]!=null){
                List<String> images = Arrays.stream(tempObj[14].toString().split(",")).toList();
                List<CommodityImageResponseDTO> commodityImageResponseDTOS =new ArrayList<>();
                for(String image : images){
                    CommodityImageResponseDTO commodityImageResponseDTO =new CommodityImageResponseDTO();
                    commodityImageResponseDTO.setCom_image(image);
                    commodityImageResponseDTOS.add(commodityImageResponseDTO);
                }
                tempDetailDTO.setCommodityImage(commodityImageResponseDTOS.toArray(new CommodityImageResponseDTO[0]));
            }
            if(tempObj[15]!=null){
                List<String> curves =Arrays.stream(tempObj[15].toString().split(",")).toList();
                List<CommodityPriceCurveResponseDTO> commodityPriceCurveResponseDTOS =new ArrayList<>();
                for(String curve : curves){
                    String[] cur =Arrays.stream(curve.split("#")).toList().toArray(new String[0]);
                    CommodityPriceCurveResponseDTO commodityPriceCurveResponseDTO = new CommodityPriceCurveResponseDTO();
                    commodityPriceCurveResponseDTO.setCom_pc_time(cur[0]);
                    commodityPriceCurveResponseDTO.setCom_pc_price(Double.parseDouble(cur[1]));
                    commodityPriceCurveResponseDTOS.add(commodityPriceCurveResponseDTO);
                }
                tempDetailDTO.setCommodityPriceCurve(commodityPriceCurveResponseDTOS.toArray
                        (new CommodityPriceCurveResponseDTO[0]));
            }
            response.add(tempDetailDTO);
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
