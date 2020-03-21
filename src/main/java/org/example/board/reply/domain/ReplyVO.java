package org.example.board.reply.domain;

import java.util.Date;

public class ReplyVO {

    private Integer replyNo;    // 댓글 번호
    private Integer ArticleNo;  // 게시글 번호
    private String replyText;   // 댓글 내용
    private String replyWriter; // 댓글 단 사람
    private Date regDate;       // 댓글 단 일자
    private Date updateDate;    // 댓글 업데이트 일자

    // getter & setter
    public Integer getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(Integer replyNo) {
        this.replyNo = replyNo;
    }

    public Integer getArticleNo() {
        return ArticleNo;
    }

    public void setArticleNo(Integer articleNo) {
        ArticleNo = articleNo;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getReplyWriter() {
        return replyWriter;
    }

    public void setReplyWriter(String replyWriter) {
        this.replyWriter = replyWriter;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    // toString()
    @Override
    public String toString() {
        return "ReplyVO{" +
                "replyNo=" + replyNo +
                ", ArticleNo=" + ArticleNo +
                ", replyText='" + replyText + '\'' +
                ", replyWriter='" + replyWriter + '\'' +
                ", regDate=" + regDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
