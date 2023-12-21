package com.sparta.plus.service;

import com.sparta.plus.dto.request.CommentSaveReq;

public interface CommentService {

    void saveComment(CommentSaveReq req, String memberName);
}
