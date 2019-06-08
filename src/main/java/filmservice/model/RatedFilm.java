package filmservice.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;


public class RatedFilm implements BaseEntity {

    private int id;

    private String title;

    private String image;

    private String description;

    private String genre;

    private LocalDate date;

    private double avgRating;

    public RatedFilm() {
    }

    public RatedFilm(Film film){
        this.id = film.getId();
        this.title = film.getTitle();
        this.image = film.getImage();
        this.description = film.getDescription();
        this.genre = film.getGenre();
        this.date = film.getDate();
        this.avgRating = calculateAvgRating(film.getListRating());
    }

    private double calculateAvgRating(List<Rating> listRating) {
        double result;
        double rawAvgRating = listRating.stream().mapToDouble(value -> value.getRating()).average().orElse(-1);
        return Double.parseDouble(String.format(Locale.ENGLISH, "%.1f", rawAvgRating));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
