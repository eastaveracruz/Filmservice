package filmservice.model;

import javax.persistence.*;

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
    public boolean isNew() {
        return this.id == null;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
