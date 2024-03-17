package org.tju.food_007.dto.cus.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentRequestDTO {
    private Integer sto_ID;
    private Integer page_size;
    private Integer page_num;
}
