package org.example.board.reply.service;

import org.example.board.article.persistence.ArticleDAO;
import org.example.board.commons.paging.Criteria;
import org.example.board.reply.persistence.ReplyDAO;
import org.example.board.reply.domain.ReplyVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyDAO replyDAO;

    private final ArticleDAO articleDAO;

    @Inject
    public ReplyServiceImpl(ReplyDAO replyDAO, ArticleDAO articleDAO) {
        this.replyDAO = replyDAO;
        this.articleDAO = articleDAO;
    }

    // 댓글 목록
    @Override
    public List<ReplyVO> getReplies(Integer articleNo) throws Exception {
        return replyDAO.list(articleNo);
    }

    // 댓글 등록
    @Override
    public void addReply(ReplyVO replyVO) throws Exception {
        replyDAO.create(replyVO);
    }

    // 댓글 수정
    @Override
    public void modifyReply(ReplyVO replyVO) throws Exception {
        replyDAO.update(replyVO);
    }

    // 댓글 삭제
    @Override
    public void removeReply(Integer replyNo) throws Exception {
        replyDAO.delete(replyNo);
    }

}
