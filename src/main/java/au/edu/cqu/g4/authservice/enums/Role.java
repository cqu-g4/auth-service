package au.edu.cqu.g4.authservice.enums;

public enum Role {

    ADMIN("ADMIN"), THERAPY_PROVIDER("THERAPY_PROVIDER"), USER("USER");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
