package com.chungdt03.holashopbe.services;

import com.chungdt03.holashopbe.dtos.CommentDTO;
import com.chungdt03.holashopbe.exceptions.payload.DataNotFoundException;
import com.chungdt03.holashopbe.models.Comment;
import com.chungdt03.holashopbe.responses.comment.CommentResponse;

import java.util.List;

public interface CommentService {
    Comment insertComment(CommentDTO comment);

    void deleteComment(Long id);

    void updateComment(Long id, CommentDTO comment) throws DataNotFoundException;

    List<CommentResponse> getCommentByUserAndProduct(Long userId, Long productId);

    List<CommentResponse> getCommentByProduct(Long productId);
}
