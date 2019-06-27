package filmservice.model.util;

import com.google.common.base.Strings;

public class GetParameters {

    private Integer userId;
    private String title;
    private Sort sort = Sort.init(Sort.TITLE_ACS);
    private String sortString;
    private String genre;
    private String assessment;

    public void setGenre(String genre) {
        if (genre != null && !"".equals(genre) && !genre.equals("-")) {
            for (Enum e : Genre.values()) {
                if (e.toString().equals(genre)) {
                    this.genre = genre;
                    break;
                }
            }
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setId(Integer id) {
        this.userId = id;
    }

    public Integer getUserId() {
        return userId;
    }


    public Sort getSort() {
        return sort;
    }


    public String getGenre() {
        return genre;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getAssessment() {
        return assessment;
    }

    public Boolean getBooleanAssessment() {
        try {
            int i = Integer.parseInt(this.assessment);
            return i == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean isGenreExist() {
        return this.genre != null;
    }

    public boolean isAssessmentExist() {
        return this.assessment != null && !this.assessment.isEmpty() && this.assessment != "-";
    }

    public boolean isTitleExist() {
        return !Strings.isNullOrEmpty(this.title);
    }

    public boolean isUserIdExist() {
        return this.userId != null;
    }

    public String getSortString() {
        return sortString;
    }

    public void setSortString(String sortString) {
        this.sort = Sort.init(sortString);
        this.sortString = sortString;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("?");
        result.append("sortString=" + sort.getOriginalString());
        if (isTitleExist()) {
            result.append("&title=" + getTitle());
        }
        if (isGenreExist()) {
            result.append("&genre=" + genre);
        }
        if (isAssessmentExist()) {
            result.append("&assessment=" + assessment);
        }
        return result.toString();

    }
}
