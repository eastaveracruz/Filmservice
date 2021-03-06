package filmservice.util.assertion;

import filmservice.model.Film;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class FilmCreationHelper {
    private static List<Film> filmsList = new CopyOnWriteArrayList<>();

    static {
        for (int i = 1; i <= 30; i++) {
            filmsList.add(new Film(i, "Film " + String.format("%02d", i),
                    "./resources/films_img/" + String.format("%d", i) + ".jpg",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.-",
                    "ACTION",
                    LocalDate.parse("2015-05-30")
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
