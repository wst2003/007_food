package org.tju.food_007.service.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.pub.balanceChangeDTO;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.pub.BalanceChangeRepository;

import java.math.BigDecimal;


/**
 * @author WGY
 * @create 2024-04-26-14:09
 */
@Service
public class BalanceChangeService {
    @Autowired
    BalanceChangeRepository balanceChangeRepository;

    public  void changeUserBalance(balanceChangeDTO requestDTO){
        UserEntity aimed_user=balanceChangeRepository.findUserEntityByUserId(requestDTO.getId());
        aimed_user.setUserBalance(BigDecimal.valueOf(requestDTO.getNew_balance()));
        balanceChangeRepository.save(aimed_user);
    }
}
