package org.tju.food_007;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tju.food_007.utils.ObsOperationTool;

@SpringBootTest
class Food007ApplicationTests {

    @Test
    void contextLoads() {
        ObsOperationTool.uploadFile("","1.png","C:\\Users\\33044\\Pictures\\3.4报销\\转接头30.6.jpg");
    }

}
