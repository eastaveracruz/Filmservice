package filmservice.repository;

import filmservice.model.Film;
import filmservice.model.util.Genre;
import filmservice.model.util.GetParameters;
import filmservice.model.util.Sort;
import filmservice.util.Pagination;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class FilmRepositoryImpl implements FilmRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Film save(Film film) {
        if (film.isNew()) {
            em.persist(film);
            return film;
        } else {
            return em.merge(film);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(Film.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Film get(int id) {
        return em.find(Film.class, id);
    }

    @Override
    @Transactional
    public List<Film> getAll(int page, GetParameters parameters) {
        TypedQuery<Film> typedQuery = querrySetting(parameters);
        return Pagination.getPaginatedResult(typedQuery, page);
    }

    @Override
    @Transactional
    public int recordsCount(GetParameters parameters) {
        TypedQuery typedQuery = querrySetting(parameters, "count");
        Long result = (Long) typedQuery.getSingleResult();
        return result.intValue();
    }

    private TypedQuery<Film> querrySetting(GetParameters parameters, String... args) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery querry;
        Root<Film> root;
        List<Predicate> predicates = new ArrayList<>();

        /*TODO Переключение между режимом выборки и счетчика*/
        if (args.length != 0 && args[0].equals("count")) {
            querry = cb.createQuery();
            root = querry.from(Film.class);
            querry.select(cb.count(root));
        } else {
            querry = cb.createQuery(Film.class);
            root = querry.from(Film.class);
            querry.select(root);

            /*Sorting*/
            Sort sort = parameters.getSort();
            switch (sort.getDirections().toLowerCase()) {
                case "asc":
                    querry.orderBy(cb.asc(root.get(sort.getString())));
                    break;
                case "desc":
                    querry.orderBy(cb.desc(root.get(sort.getString())));
                    break;
                default:
                    querry.orderBy(cb.asc(root.get("title")));
            }
        }

        /*Title*/
        if (parameters.isTitleExist()) {
            String title = parameters.getTitle();
            querry.where(cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%"));
        }

        /*Genre*/
        if (parameters.isGenreExist()) {
            Genre genre = parameters.getGenre();
            predicates.add(cb.equal(root.get("genre"), genre.name()));
        }

        /*Assessment*/
        if (parameters.isAssessmentExist() && parameters.getUserId() != null) {

            List list = em.createQuery("SELECT r.filmId FROM Rating r where r.userId = :userId")
                    .setParameter("userId", parameters.getUserId())
                    .getResultList();


            /*TODO Если list пустой то predicates.add(root.get("id").in(list)) бросает эксепшен*/
            if (list.size() == 0) {
                list.add(-1);
            }

            if (parameters.getAssessment()) {
                predicates.add(root.get("id").in(list));
            } else {
                predicates.add(cb.not(root.get("id").in(list)));
            }
        }

        if (predicates.size() != 0) {
            Predicate[] predicatesArray = predicates.stream().toArray(Predicate[]::new);
            querry.where(predicatesArray);
        }

        return em.createQuery(querry);
    }
}