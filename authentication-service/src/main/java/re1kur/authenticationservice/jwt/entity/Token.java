package re1kur.authenticationservice.jwt.entity;

public record Token(String body) {
    public String toString() {
        return this.body;
    }
}
