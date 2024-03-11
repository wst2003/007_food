package org.tju.food_007.dto.cus.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentResponseDTO {
    private int cmt_ID;
    private String cmt_content;
    private String cmt_time;
    private int ind_ID;
    private int user_ID;
    private String user_name;
    private String user_logo;
}
