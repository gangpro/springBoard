package org.example.board.upload.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 첨부파일의 정보를 테이블에 저장하기 위해
@Repository
public class ArticleFileDAOImpl implements ArticleFileDAO {

    private static final String NAMESPACE = "org.example.board.mappers.upload.ArticleFileMapper";

    private final SqlSession sqlSession;

    @Inject
    public ArticleFileDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 파일 추가
    @Override
    public void addFile(String fileName) throws Exception {
        sqlSession.insert(NAMESPACE + ".addFile", fileName);
    }

    // 파일 목록
    @Override
    public List<String> getArticleFiles(Integer articleNo) throws Exception {
        return sqlSession.selectList(NAMESPACE + ".getArticleFiles", articleNo);
    }

    // 파일 전체 삭제
    @Override
    public void deleteFiles(Integer articleNo) throws Exception {
        sqlSession.delete(NAMESPACE + ".deleteFiles", articleNo);
    }

    // 파일 삭제
    @Override
    public void deleteFile(String fileName) throws Exception {
        sqlSession.delete(NAMESPACE + ".deleteFile", fileName);
    }

    // 파일 수정
    @Override
    public void replaceFile(String fileName, Integer articleNo) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fileName", fileName);
        paramMap.put("articleNo", articleNo);
        sqlSession.insert(NAMESPACE + ".replaceFile", paramMap);
    }

    // 파일 개수 갱신
    @Override
    public void updateFileCnt(Integer articleNo) throws Exception {
        sqlSession.update(NAMESPACE + ".updateFileCnt", articleNo);
    }

}
