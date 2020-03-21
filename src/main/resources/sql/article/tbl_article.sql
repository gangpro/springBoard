-- 게시글 테이블 삭제
DROP TABLE tbl_article;

-- 게시글 테이블 생성
CREATE TABLE tbl_article
(article_no INT NOT NULL AUTO_INCREMENT
    ,title VARCHAR(200) NOT NULL
    ,content TEXT NULL
    ,writer VARCHAR(50) NOT NULL
    ,regdate TIMESTAMP NOT NULL DEFAULT NOW()
    ,viewcnt INT DEFAULT 0
    ,PRIMARY KEY (article_no)
);

SELECT * FROM tbl_article;

SELECT COUNT(*)
  FROM tbl_article;

-- 댓글 갯수 칼럼 추가
ALTER TABLE tbl_article ADD COLUMN reply_cnt INT DEFAULT 0;

-- 조회수 칼럼명 변경
ALTER TABLE tbl_article CHANGE COLUMN viewcnt view_cnt INT DEFAULT 0;

-- 등록일 칼럼명 변경
ALTER TABLE tbl_article CHANGE COLUMN regdate reg_date TIMESTAMP DEFAULT NOW();