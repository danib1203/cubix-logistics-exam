package hu.cubix.logistics.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class LogisticsUser {
    @Id
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> roles;

    public LogisticsUser() {
    }

    public LogisticsUser(String username, String password, Set<String> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
