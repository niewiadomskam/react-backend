package pw.react.backend.reactbackend.Models;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable = false)
    private String FirstName;

    @Column(nullable = false)
    private String LastName;

    private LocalDate BirthDate;

    @Column(nullable = false)
    private String Login;

    @Column(nullable = false)
    private boolean IsActive;


    public Long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        IsActive = isActive;
    }

    public User setAllData(String login, String firstName, String lastName, LocalDate birthDate, boolean isActive){
        this.setLogin(login);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setBirthDate(birthDate);
        this.setIsActive(isActive);
    }
}