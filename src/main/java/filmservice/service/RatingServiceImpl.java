package filmservice.service;

import filmservice.model.Rating;
import filmservice.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static filmservice.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository repository;

    @Override
    public Rating save(Rating rating) {
        Assert.notNull(rating, "film must be not null");
        return checkNotFoundWithId(repository.save(rating), rating.getId());
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public double getAvgFilmRating(int filmId){
        return repository.getAvgFilmRating(filmId);
    }
}
