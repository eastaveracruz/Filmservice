package filmservice.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


@NamedQueries({
        @NamedQuery(name = Film.GET_ALL, query = "SELECT f FROM Film f"),
        @NamedQuery(name = Film.GET_BY_TITLE, query = "SELECT f FROM Film f WHERE upper(f.title) LIKE upper(:title)"),
        @NamedQuery(name = Film.DELETE, query = "DELETE FROM Film f WHERE f.id=:id"),
})
@Entity
@Table(name = "films")
public class Film implements BaseEntity {
    public static final int START_SEQ = 1;

    public static final String GET_ALL = "Film.get_all";
    public static final String GET_BY_TITLE = "Film.get_by_title";
    public static final String DELETE = "Film.delete";

    @Id
    @SequenceGenerator(name = "film_seq", sequenceName = "film_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_seq")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "genre")
    private String genre;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "rating")
    private Double rating;

    @Transient
    private MultipartFile file;

    @Transient
    private String rawDate;


    public Film() {
    }

    public Film(String title, String image, String description, String genre, LocalDate date) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.genre = genre;
        this.date = date;
    }

    public Film(Integer id, String title, String image, String description, String genre, LocalDate date) {
        this(title, image, description, genre, date);
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Double.compare(film.rating, rating) == 0 &&
                Objects.equals(id, film.id) &&
                Objects.equals(title, film.title) &&
                Objects.equals(image, film.image) &&
                Objects.equals(description, film.description) &&
                Objects.equals(genre, film.genre) &&
                Objects.equals(date, film.date) &&
                Objects.equals(file, film.file) &&
                Objects.equals(rawDate, film.rawDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, image, description, genre, date, rating, file, rawDate);
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", date=" + date +
                ", rating=" + rating +
                '}';
    }

    public void setId(Integer id) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getRawDate() {
        return rawDate;
    }

    public void setRawDate(String rawDate) {
        this.rawDate = rawDate;
    }

}
