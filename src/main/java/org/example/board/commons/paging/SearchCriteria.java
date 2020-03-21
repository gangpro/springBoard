package org.example.board.commons.paging;

public class SearchCriteria extends Criteria {

    private String searchType;  // 검색 타입
    private String keyword;     // 검색 키워드

    // getter & setter
    public String getSearchType() {

        return searchType;
    }

    public void setSearchType(String searchType) {

        this.searchType = searchType;
    }

    public String getKeyword() {

        return keyword;
    }

    public void setKeyword(String keyword) {

        this.keyword = keyword;
    }

    // toString()

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "searchType='" + searchType + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
