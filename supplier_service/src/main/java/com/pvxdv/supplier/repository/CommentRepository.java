package com.pvxdv.supplier.repository;

import com.pvxdv.supplier.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    Page<Comment> findAllByProductId(Long productId, Pageable pageable);
    List<Comment> findAllByProductId(Long productId);
    @Query(value = "SELECT AVG(comments.rating) FROM comments GROUP BY products_id HAVING products_id=?1", nativeQuery = true)
    Integer findProductAVGRatingById(Long id);
}
