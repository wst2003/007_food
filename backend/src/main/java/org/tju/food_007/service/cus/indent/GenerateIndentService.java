package org.tju.food_007.service.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.cus.indent.GenerateIndentRequestDTO;
import org.tju.food_007.dto.cus.indent.IndentCommodity;
import org.tju.food_007.dto.cus.mapper.GenerateIndentComRequstMapper;
import org.tju.food_007.dto.cus.mapper.GenerateIndentRequestMapper;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.repository.cus.indent.GenerateIndentComRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentRepository;

/**
 * @author WGY
 * @create 2024-03-17-16:00
 */
import java.math.BigDecimal;
import java.util.UUID;



@Service
public class GenerateIndentService {
    @Autowired
    GenerateIndentRepository generateIndentRepository;
    @Autowired
    GenerateIndentComRepository generateIndentComRepository;
    private final GenerateIndentComRequstMapper generateIndentComRequstMapper=GenerateIndentComRequstMapper.INSTANCE;
    private final GenerateIndentRequestMapper generateIndentRequestMapper=GenerateIndentRequestMapper.INSTANCE;

    public static String generateVerificationCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    @Transactional
    public String GenerateIndent(GenerateIndentRequestDTO requestDTO){
        IndentEntity newIndent=generateIndentRequestMapper.dtoToEntity(requestDTO);
        newIndent.setIndVerificationCode(generateVerificationCode());
        newIndent.setFoodQualityScore(BigDecimal.valueOf(5.0));
        newIndent.setServiceQualityScore(BigDecimal.valueOf(5.0));
        newIndent.setPricePerformanceRatio(BigDecimal.valueOf(5.0));
        IndentEntity inserted_ind=generateIndentRepository.save(newIndent);
        for (IndentCommodity com : requestDTO.getCom_arr()) {
            IndentCommodityEntity newCom= generateIndentComRequstMapper.dtoToEntity(com);
            System.out.println(newCom.getComId());
            newCom.setIndId(inserted_ind.getIndId());
            newCom.setRatingType(1);
            IndentCommodityEntity inserted_com=generateIndentComRepository.save(newCom);
        }

        IndentCommodity dto =new IndentCommodity();

        return "成功生成订单";
    }

}
