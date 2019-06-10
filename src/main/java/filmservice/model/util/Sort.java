package filmservice.model.util;

public class Sort {

    private String directions;
    private String string;
    private String originalString;

    public Sort(String directions, String string, String originalString) {
        this.directions = directions;
        this.string = string;
        this.originalString = originalString;
    }

    public static Sort init(String string) {

        if (string == null) {
            return new Sort("asc", "title", "title_asc");
        }

        String[] parameters = string.split("_");
        String directions = parameters[1];
        String sortParameter = parameters[0];

        String dir;
        String str;
        switch (directions) {
            case "asc":
            case "desc":
                dir = directions;
                break;
            default:
                dir = "asc";
        }

        switch (sortParameter) {
            case "title":
            case "genre":
            case "rating":
            case "date":
                str = sortParameter;
                break;
            default:
                str = "title";
        }
        return new Sort(dir, str, string);
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }
}
