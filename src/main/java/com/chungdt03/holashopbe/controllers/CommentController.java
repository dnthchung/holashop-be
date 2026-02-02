package com.chungdt03.holashopbe.controllers;

import com.chungdt03.holashopbe.components.TranslateMessages;
import com.chungdt03.holashopbe.dtos.CommentDTO;
import com.chungdt03.holashopbe.models.Comment;
import com.chungdt03.holashopbe.responses.ApiResponse;
import com.chungdt03.holashopbe.responses.comment.CommentResponse;
import com.chungdt03.holashopbe.services.CommentService;
import com.chungdt03.holashopbe.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comments")
@RequiredArgsConstructor
public class CommentController extends TranslateMessages {

    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getAllComments(
            @RequestParam(value = "user_id", required = false) Long userId,
            @RequestParam("product_id") Long productId
    ) {
        List<CommentResponse> commentResponses;
        if (userId != null) {
            commentResponses = commentService.getCommentByUserAndProduct(userId, productId);
        } else {
            commentResponses = commentService.getCommentByProduct(productId);
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .payload(commentResponses)
                .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComments(@PathVariable("id") Long commentId,
                                            @Valid @RequestBody CommentDTO commentDTO,
                                            Authentication authentication) {
        try {
            commentService.updateComment(commentId, commentDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message(translate(MessageKeys.UPDATE_COMMENT_SUCCESS))
                    .build());
        } catch (Exception e) {
            // handle and log exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .error(e.getMessage())
                            .message(translate(MessageKeys.ERROR_MESSAGE))
                            .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> insertComments(@Valid @RequestBody CommentDTO commentDTO,
                                            Authentication authentication) {
        try {
            Comment newComment = commentService.insertComment(commentDTO);
            return ResponseEntity.ok(ApiResponse.builder().success(true)
                    .message(translate(MessageKeys.COMMENT_INSERT_SUCCESS))
                    .payload(newComment)
                    .build());
        } catch (Exception e) {
            // handle and log exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .error(e.getMessage())
                            .message(translate(MessageKeys.ERROR_MESSAGE))
                            .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok(ApiResponse.builder().success(true)
                    .message(translate(MessageKeys.DELETE_COMMENT_SUCCESS))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .error(e.getMessage())
                    .message(translate(MessageKeys.DELETE_COMMENT_FAILED))
                    .build());
        }
    }

}
