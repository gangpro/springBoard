package org.example.board.reply.persistence;

import org.apache.ibatis.session.SqlSession;
import org.example.board.commons.paging.Criteria;
import org.example.board.reply.domain.ReplyVO;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

    // 변수 NAMESPACE 는 articleMapper.xml 의 <mapper> 태그의 namespace 속성과 일치해야 한다.
    private static String NAMESPACE = "org.example.board.mappers.reply.ReplyMapper";

    private final SqlSession sqlSession;

    @Inject
    public ReplyDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 댓글 목록
    @Override
    public List<ReplyVO> list(Integer articleNo) throws Exception {
        return sqlSession.selectList(NAMESPACE + ".list", articleNo);
    }

    // 댓글 등록
    @Override
    public void create(ReplyVO replyVO) throws Exception {
        sqlSession.insert(NAMESPACE + ".create", replyVO);
    }

    // 댓글 수정
    @Override
    public void update(ReplyVO replyVO) throws Exception {
        sqlSession.update(NAMESPACE + ".update", replyVO);
    }

    // 댓글 삭제
    @Override
    public void delete(Integer replyNo) throws Exception {
        sqlSession.delete(NAMESPACE + ".delete", replyNo);
    }

    // 댓글 페이징 목록
    @Override
    public List<ReplyVO> listPaging(Integer articleNo, Criteria criteria) throws Exception {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("articleNo", articleNo);
        paramMap.put("criteria", criteria);

        return sqlSession.selectList(NAMESPACE + ".listPaging", paramMap);
    }

    // 댓글 페이징 카운팅
    @Override
    public int countReplies(Integer articleNo) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".countReplies", articleNo);
    }

}
