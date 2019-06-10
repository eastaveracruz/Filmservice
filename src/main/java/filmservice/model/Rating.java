package filmservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ratings")
public class Rating implements BaseEntity {

    public static final int START_SEQ = 1;

    @Id
    @SequenceGenerator(name = "rating_seq", sequenceName = "rating_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_seq")
    private Integer id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "film_id")
    private int filmId;

    @Column(name = "rating")
    private int rating;

    public Rating() {
    }

    public Rating(int userId, int filmId, int rating) {
        this.userId = userId;
        this.filmId = filmId;
        this.rating = rating;
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
    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return userId == rating1.userId &&
                filmId == rating1.filmId &&
                rating == rating1.rating &&
                Objects.equals(id, rating1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, filmId, rating);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", userId=" + userId +
                ", filmId=" + filmId +
                ", rating=" + rating +
                '}';
    }
}
