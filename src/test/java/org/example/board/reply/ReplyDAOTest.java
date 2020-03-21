package org.example.board.reply;

import org.example.board.commons.paging.Criteria;
import org.example.board.reply.persistence.ReplyDAO;
import org.example.board.reply.domain.ReplyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml"})
public class ReplyDAOTest {

    private static final Logger logger = LoggerFactory.getLogger(ReplyDAOTest.class);

    @Inject
    private ReplyDAO replyDAO;

    // 댓글 등록 테스트
    @Test
    public void testReplyCreate() throws Exception {

        for (int i = 1; i <= 100; i++) {
            ReplyVO replyVO = new ReplyVO();
            replyVO.setArticleNo(1000);
            replyVO.setReplyText(i + "번째 댓글입니다...");
            replyVO.setReplyWriter("user0" + (i % 10));

            replyDAO.create(replyVO);
        }
    }

    // 댓글 목록 테스트
    @Test
    public void testReplyList() throws Exception {
        logger.info(replyDAO.list(1000).toString());
    }

    // 댓글 수정 테스트
    @Test
    public void testReplyUpdate() throws Exception {
        ReplyVO replyVO = new ReplyVO();
        replyVO.setArticleNo(2);
        replyVO.setReplyText(2 + "번째 댓글 수정...");

        replyDAO.update(replyVO);
    }

    // 댓글 삭제 테스트
    @Test
    public void testReplyDelete() throws Exception {
        replyDAO.delete(3);
    }

    // 댓글 페이징 테스트
    @Test
    public void testReplyPaging() throws Exception {
        Criteria criteria = new Criteria();
        criteria.setPerPageNum(20);
        criteria.setPage(1);

        List<ReplyVO> replies = replyDAO.listPaging(1000, criteria);

        for(ReplyVO reply : replies) {
            logger.info(reply.getReplyNo() + " : " + reply.getReplyText());
        }
    }

}
