package org.example.board.reply.persistence;

import org.example.board.reply.domain.ReplyVO;
import org.example.board.commons.paging.Criteria;

import java.util.List;

public interface ReplyDAO {

    // 댓글 처리
    List<ReplyVO> list(Integer articleNo) throws Exception;
    void create(ReplyVO replyVO) throws Exception;
    void update(ReplyVO replyVO) throws Exception;
    void delete(Integer replyNo) throws Exception;

    // 댓글 페이징 처리
    List<ReplyVO> listPaging(Integer articleNo, Criteria criteria) throws Exception;
    int countReplies(Integer articleNo) throws Exception;

}
