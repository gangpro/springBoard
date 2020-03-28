package org.example.board.article.domain;

import java.util.Arrays;
import java.util.Date;

// 테이블 구조를 객체화 시킬 때 사용할 ArticleVO 클래스
public class ArticleVO {

    private Integer articleNo;  // 게시글 번호
    private String title;       // 게시글 제목
    private String content;     // 게시글 내용
    private String writer;      // 작성자
    private Date regDate;       // 작성 날짜
    private int viewCnt;        // 조회수
    private int replyCnt;       // 댓글 갯수

    private String[] files;     // 파일       // 다수의 첨부파일 이름을 저장하기 위해 String 배열 타입 변수 files 선언
    private int fileCnt;        // 파일 개수    // 첨부파일의 개수를 의미하는 fileCnt 변수를 선언

    public Integer getArticleNo() {
        return articleNo;
    }

    public void setArticleNo(Integer articleNo) {
        this.articleNo = articleNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }

    // 게시글이 입력/수정 될 때 ArticleVO 의 인스턴스가 스스로 게시글의 첨부파일 개수의 상태를 가질 수 있게 setFiles() 메서드 작성
    // 이렇게 되면 외부에서 setFiles()를 호출하여 게시글의 첨부파일 개수를 넣어주지 않아도 된다.
    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
        setFileCnt(files.length);
    }

    public int getFileCnt() {
        return fileCnt;
    }

    public void setFileCnt(int fileCnt) {
        this.fileCnt = fileCnt;
    }

    // toString()
    @Override
    public String toString() {
        return "ArticleVO{" +
                "articleNo=" + articleNo +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", regDate=" + regDate +
                ", viewCnt=" + viewCnt +
                ", replyCnt=" + replyCnt +
                ", files=" + Arrays.toString(files) +
                ", fileCnt=" + fileCnt +
                '}';
    }
}
