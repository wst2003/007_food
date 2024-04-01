package org.tju.food_007.service.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.utils.OpenAIAPI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WGY
 * @create 2024-03-28-19:44
 */
@Service
public class GptService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GptService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public ArrayList<Integer> AISearch(String words){
        ArrayList<Integer> com_ids=new ArrayList<Integer>();
        String sql=OpenAIAPI.chat(words);
        System.out.println(sql);
        List<CommodityEntity> coms=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommodityEntity.class));
        for (CommodityEntity com:coms)
        {
            com_ids.add(com.getComId());
            System.out.println(com.getComId());
        }

        return com_ids;
    }
}
