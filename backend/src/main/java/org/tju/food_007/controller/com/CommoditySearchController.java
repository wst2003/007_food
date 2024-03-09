package org.tju.food_007.controller.com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.com.CommodityDetailDTO;
import org.tju.food_007.dto.com.SearchCommodityRequestDTO;
import org.tju.food_007.dto.com.SearchCommodityResponseDTO;
import org.tju.food_007.service.com.CommoditySearchService;

import java.util.List;

@RestController
@RequestMapping("/api/com")
public class CommoditySearchController {
    @Autowired
    CommoditySearchService commoditySearchService;

    @RequestMapping(value = "/searchCommodity",method = RequestMethod.GET)
    public ResponseEntity<SearchCommodityResponseDTO[]> searchCommodity(@RequestParam(required = false) String content,
                                                            @RequestParam(required = false) String com_type,
                                                            @RequestParam(required = false) Integer sort_by,
                                                            @RequestParam(required = false) Integer sort_order,
                                                            @RequestParam(required = false) Integer page_size,
                                                            @RequestParam(required = false) Integer page_num) {
        SearchCommodityRequestDTO requestDTO = new SearchCommodityRequestDTO();
        requestDTO.setContent(content);

        requestDTO.setCom_type(com_type);

        requestDTO.setSort_by(sort_by);
        requestDTO.setSort_order(sort_order);
        requestDTO.setPage_size(page_size);
        requestDTO.setPage_num(page_num);

        List<SearchCommodityResponseDTO> responseDTO = commoditySearchService.searchCommodity(requestDTO);

        return new ResponseEntity<>(responseDTO.toArray(new SearchCommodityResponseDTO[0]), HttpStatus.OK);
    }

}
