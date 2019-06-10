package filmservice.util;

import filmservice.Settings;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Component
public class Pagination {

    private static int maxResult = Settings.PAGINATION_MAX_RESULTS;
    private static int radius = Settings.PAGINATION_RADIUS;

    private Pagination() {
    }

    public static <E> List<E> getPaginatedResult(Query query, int page) {
        query.setFirstResult((page - 1) * maxResult);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    public static String generatePaginationBlock(int page, int numberOfRecords, String stringParameters) {

        int numberOfPage = numberOfRecords / maxResult;
        String result = "<ul class=pagination>";
        if (page > (radius - 1)) {
            result += "<li><a href=" + 1 + stringParameters + "><<</a></li>";
        }
        for (int i = page - radius; i < page + radius; i++) {
            if (i < 1) {
                continue;
            }
            if (i > numberOfPage) {
                break;
            }
            if (i == page) {
                result += "<li class=currentPage><a href=./" + i + stringParameters + ">" + i + "</a></li>";
                continue;
            }
            result += "<li><a href=./" + i + stringParameters + ">" + i + "</a></li>";
        }
        if (page < numberOfPage - (radius - 1)) {
            result += "<li><a href=" + numberOfPage + stringParameters + ">>></a></li>";
        }
        result += "</ul>";

        return result;
    }

    public static int pageValid(Integer page, int recordsCount) {
        if (page == null || page > recordsCount || page < 1) {
            return 1;
        }
        return page;
    }

    private static String generateParametersString(Map<String, String> parameters) {
        StringBuilder result = new StringBuilder("?");
        parameters.forEach((o, o2) -> result.append(String.format("%s=%s&", o, o2)));
        result.deleteCharAt(result.lastIndexOf("&"));
        return result.toString();
    }


}
