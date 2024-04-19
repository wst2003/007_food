package org.tju.food_007.repository.cus.indent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tju.food_007.model.CommentEntity;

/**
 * @author WGY
 * @create 2024-03-17-20:45
 */
public interface IndentCommentRopsitory extends JpaRepository<CommentEntity,Integer> {
    CommentEntity findByIndId(Integer ind_id);
}
