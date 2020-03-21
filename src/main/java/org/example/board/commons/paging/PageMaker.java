package org.example.board.commons.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PageMaker {

    // 외부에서 입력되는 데이터 : page, perPageNum
    // DB 에서 계산되는 데이터 : totalCount
    // 계산식을 통해 만들어지는 데이터 : startPage, endPage, prev, next

    private int totalCount; // 전체 게시글의 갯수
    private int startPage;  // 시작 페이지 번호
    private int endPage;    // 끝 페이지 번호
    private boolean prev;   // 이전 링크
    private boolean next;   // 다음 링크

    // 하단의 페이지 번호의 갯수를 의미
    private int displayPageNum = 10;

    private Criteria criteria;

    public void setCriteria(Criteria criteria) {

        this.criteria = criteria;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();
    }

    // 게시글의 전체 갯수가 설정되는 시점에 calcData() 메서드를 호출하여 필요한 데이터들를 계산
    private void calcData() {

        endPage = (int)(Math.ceil(criteria.getPage() / (double) displayPageNum) * displayPageNum);
        startPage = (endPage - displayPageNum) + 1;

        int tempEndPage = (int)(Math.ceil(totalCount / (double) criteria.getPerPageNum()));

        if(endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        prev = startPage == 1 ? false : true;
        next = endPage * criteria.getPerPageNum() >= totalCount ? false : true;
    }

    // URI 자동 생성
    public String makeQuery(int page) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("perPageNum", criteria.getPerPageNum())
                .build();

        return uriComponents.toUriString();
    }
//
//    // 검색조건과 검색키워드를 처리를 메서드
//    public String makeSearch(int page) {
//        UriComponents uriComponents = UriComponentsBuilder.newInstance()
//                .queryParam("page", page)
//                .queryParam("pagePageNum", criteria.getPerPageNum())
//                .queryParam("searchType", ((SearchCriteria) criteria).getSearchType())
//                .queryParam("keyword", encoding(((SearchCriteria)criteria).getKeyword()))
//                .build();
//
//        return uriComponents.toUriString();
//    }
//
//    // 검색키워드를 인코딩 처리 메서드
//    private String encoding(String keyword) {
//        if (keyword == null || keyword.trim().length() == 0) {
//            return "";
//        }
//        try {
//            return URLEncoder.encode(keyword, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            return "";
//        }
//    }

    public int getTotalCount() {

        return totalCount;
    }

    public int getStartPage() {

        return startPage;
    }

    public void setStartPage(int startPage) {

        this.startPage = startPage;
    }

    public int getEndPage() {

        return endPage;
    }

    public void setEndPage(int endPage) {

        this.endPage = endPage;
    }

    public boolean isPrev() {

        return prev;
    }

    public void setPrev(boolean prev) {

        this.prev = prev;
    }

    public boolean isNext() {

        return next;
    }

    public void setNext(boolean next) {

        this.next = next;
    }

    public int getDisplayPageNum() {

        return displayPageNum;
    }

    public void setDisplayPageNum(int displayPageNum) {

        this.displayPageNum = displayPageNum;
    }

    public Criteria getCriteria() {

        return criteria;
    }

    @Override
    public String toString() {
        return "PageMaker{" +
                "totalCount=" + totalCount +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", prev=" + prev +
                ", next=" + next +
                ", displayPageNum=" + displayPageNum +
                ", criteria=" + criteria +
                '}';
    }
}
