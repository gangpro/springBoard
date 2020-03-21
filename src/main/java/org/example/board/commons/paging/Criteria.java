package org.example.board.commons.paging;

public class Criteria {

    private int page;       // 현재 페이지 번호
    private int perPageNum; // 페이 당 출력되는 게시글의 갯수

    // Constructor
    // 기본 생성자, 현재 페이지를 1, 페이지 당 출력할 게시글의 갯수를 10으로 기본 세팅
    public Criteria() {
        this.page = 1;
        this.perPageNum = 10;
    }

    // getter&setter
    // set 메서드 : 음수와 같은 잘못된 값이 들어오지 않도록 validation 체크를 통해 적절한 값으로 세팅
    // get 메서드 : SQL Mapper 가 사용할 get 메서드를 정의
    public int getPage() {

        return page;
    }

    // 음수와 같은 잘못된 값이 들어오지 않도록 validation 체크를 통해 적절한 값으로 세팅
    public void setPage(int page) {

        if(page <= 0) {
            this.page = 1;
            return;
        }

        this.page = page;
    }

    public int getPerPageNum() {

        return this.perPageNum;
    }

    // 음수와 같은 잘못된 값이 들어오지 않도록 validation 체크를 통해 적절한 값으로 세팅
    public void setPerPageNum(int perPageNum) {

        if(perPageNum <= 0 || perPageNum > 100) {
            this.perPageNum = 10;
            return;
        }

        this.perPageNum = perPageNum;
    }

    // SQL Mapper 의 LIMIT 구문에서 현재 페이지의 게시글의 시작위치를 지정할 때 사용한다.
    // 예를 들어 10개씩 출력할 경우, 3 페이지는 SQL 이 LIMIT 20, 10과 같은 형태가 되어야 한다.
    // 아래는 20을 얻기 이한 계산 공식이다.
    // "현재 페이지의 시작 게시글 번호 = (현재 페이지 번호 - 1) * 페이지 당 출력할 게시글의 갯수"
    public int getPageStart() {

        return (this.page -1) * perPageNum;
    }

    // toString()
    @Override
    public String toString() {
        return "Criteria{" +
                "page=" + page +
                ", perPageNum=" + perPageNum +
                '}';
    }
}
