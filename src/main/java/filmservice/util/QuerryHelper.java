package filmservice.util;

import filmservice.model.util.Genre;
import filmservice.model.util.GetParameters;
import filmservice.model.util.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class QuerryHelper<E> {

    private boolean counter = false;
    private GetParameters parameters;
    private EntityManager em;
    private Class clazz;

    private CriteriaBuilder cb;
    private CriteriaQuery querry;
    private Root<E> root;
    List<Predicate> predicates = new ArrayList<>();

    public QuerryHelper(EntityManager em, GetParameters parameters, Class clazz) {
        this.parameters = parameters;
        this.em = em;
        this.clazz = clazz;
        this.cb = em.getCriteriaBuilder();
    }

    public boolean isCounter() {
        return counter;
    }

    public void setCounter(boolean counter) {
        this.counter = counter;
    }

    public TypedQuery<E> createQuerry() {

        if (counter) {
            counterMod();
        } else {
            sampleMod();
            sort();
        }

        if (parameters.isTitleExist()) {
            titleFilter();
        }

        if (parameters.isGenreExist()){
            genreFilter();
        }

        if (parameters.isAssessmentExist()){
            assessmentFilter();
        }

        if (predicates.size() != 0) {
            Predicate[] predicatesArray = predicates.stream().toArray(Predicate[]::new);
            querry.where(predicatesArray);
        }

        return em.createQuery(querry);
    }

    private void sampleMod() {
        querry = cb.createQuery(clazz);
        root = querry.from(clazz);
        querry.select(root);


    }

    private void counterMod() {
        querry = cb.createQuery();
        root = querry.from(clazz);
        querry.select(cb.count(root));
    }

    private void sort() {
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

    private void titleFilter() {
        String title = parameters.getTitle();
        predicates.add(cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%"));
    }

    private void genreFilter() {
        Genre genre = parameters.getGenre();
        predicates.add(cb.equal(root.get("genre"), genre.name()));
    }

    private void assessmentFilter() {
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

}
