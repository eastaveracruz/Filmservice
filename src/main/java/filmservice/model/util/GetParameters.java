package filmservice.model.util;

import com.google.common.base.Strings;

public class GetParameters {

    private Integer userId;
    private String title;
    private Sort sort;
    private Genre genre;
    private Boolean assessment;



    public void setGenre(String genre) {
        if (genre != null && !"".equals(genre)) {
            for (Enum e : Genre.values()) {
                if (e.equals(Genre.valueOf(genre))) {
                    this.genre = Genre.valueOf(genre);
                    break;
                }
            }
        }
    }

    public void setAssessment(String assessment) {
        try {
            int i = Integer.parseInt(assessment);
            this.assessment = i == 1;
        } catch (Exception e) {
            this.assessment = null;
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


    public Genre getGenre() {
        return genre;
    }

    public Boolean getAssessment() {
        return assessment;
    }

    public String getTitle() {
        return title;
    }

    public boolean isGenreExist() {
        return this.genre != null;
    }

    public boolean isAssessmentExist() {
        return this.assessment != null;
    }

    public boolean isTitleExist() {
        return !Strings.isNullOrEmpty(this.title);
    }

    public boolean isUserIdExist(){
        return this.userId != null;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("?");
        result.append("&sort=" + sort.getOriginalString());
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
