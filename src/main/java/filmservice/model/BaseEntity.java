package filmservice.model;

public interface BaseEntity {
    boolean isNew();

    void setId(int id);

    int getId();
}
