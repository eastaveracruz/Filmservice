package filmservice.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;

@NamedQueries({
        @NamedQuery(name = User.GET_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.GET_BY_LOGIN, query = "SELECT DISTINCT u FROM User u WHERE u.login=:login"),
})
@Entity
@Table(name = "users")
//@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class User implements BaseEntity {
    public static final int START_SEQ = 1;

    public static final String GET_ALL = "User.get_all";
    public static final String DELETE = "User.delete";
    public static final String GET_BY_LOGIN = "User.get_by_login";

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @MapKey(name = "filmId")
    private Map<Integer, Rating> rating;

    @Transient
    private String confirmPassword;

    public Map<Integer, Rating> getRating() {
        return rating;
    }

    public void setRating(Map<Integer, Rating> rating) {
        this.rating = rating;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User() {
    }

    public User(int id, String login, String password, Role role, Role... roles ) {
        this(login, password, EnumSet.of(role, roles));
        this.id = id;
    }

    public User(String login, String password, Collection<Role> roles){
        this.login = login;
        this.password = password;
        setRoles(roles);
    }

    public User(String login, String password, Role... roles){
        this.login = login;
        this.password = password;
        this.roles = EnumSet.copyOf(Arrays.asList(roles));
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + roles +
                '}';
    }
}
