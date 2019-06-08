package filmservice.model.DTO;

import java.util.List;

public class FilmTO<E> {

    private List<E> result;

    private String paginationBlock;

    public FilmTO(List<E> result, String paginationBlock) {
        this.result = result;
        this.paginationBlock = paginationBlock;
    }

    public List<E> getResult() {
        return result;
    }

    public String getPaginationBlock() {
        return paginationBlock;
    }
}
