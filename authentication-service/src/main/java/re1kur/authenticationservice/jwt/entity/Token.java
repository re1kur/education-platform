package re1kur.authenticationservice.jwt.entity;

public record Token(String header, String payload, String signature) {
    public String toString() {
        return this.header() + "."
               + this.payload() + "."
               + this.signature();
    }
}
