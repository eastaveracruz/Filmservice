package filmservice.util.assertion;

import filmservice.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class FilmCreationHelper {
    private static List<Film> filmsList = new CopyOnWriteArrayList<>();

    static {
        for (int i = 1; i <= 11; i++) {
            filmsList.add(new Film(i, "Film " + String.format("%02d", i),
                    "./resources/films_img/xmen" + String.format("%02d", i) + ".jpg",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    "ACTION"
            ));
        }
    }

    public static List<Film> getFilmsList() {
        return new ArrayList<>(filmsList);
    }

    public static Map<Integer, Film> getFilmMap() {
        return filmsList.stream().collect(Collectors.toMap(Film::getId, o -> o));
    }

}
