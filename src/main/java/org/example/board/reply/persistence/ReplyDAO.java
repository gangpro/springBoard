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

    // 댓글의 게시글 번호 조회 메서드 선언 구현
    // 댓글이 삭제될 때 해당 게시물의 번호를 조회하는 기능을 추가한다.
    // 이 기능을 통해 조회한 게시글의 번호를 가지고 댓글 갯수를 갱신하는 작업을 수행하게 된다.
    int getArticleNo(Integer replyNo) throws Exception;
}
