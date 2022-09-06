package com.back.miru.model.dto;

public class ListParameterDto {
    private int start;
    private int currentPerPage;
    private String keyword;
    private String sortKeyword;
    private boolean isPicture;
    private String id;

    public ListParameterDto() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCurrentPerPage() {
        return currentPerPage;
    }

    public void setCurrentPerPage(int currentPerPage) {
        this.currentPerPage = currentPerPage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSortKeyword() {
        return sortKeyword;
    }

    public void setSortKeyword(String sortKeyword) { this.sortKeyword = sortKeyword; }

    public boolean getIsPicture() { return isPicture; }

    public void setIsPicture(boolean isPicture) { this.isPicture = isPicture; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ListParameterDto{" +
                "start=" + start +
                ", currentPerPage=" + currentPerPage +
                ", keyword='" + keyword + '\'' +
                ", sortKeyword='" + sortKeyword + '\'' +
                ", isPicture=" + isPicture +
                ", id='" + id + '\'' +
                '}';
    }
}
