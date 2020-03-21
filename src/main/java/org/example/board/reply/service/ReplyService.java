package org.example.board.reply.service;

import org.example.board.commons.paging.Criteria;
import org.example.board.reply.domain.ReplyVO;

import java.util.List;

public interface ReplyService {

    // 댓글 처리
    List<ReplyVO> getReplies(Integer articleNo) throws Exception;
    void addReply(ReplyVO replyVO) throws Exception;
    void modifyReply(ReplyVO replyVO) throws Exception;
    void removeReply(Integer replyNo) throws Exception;

}
