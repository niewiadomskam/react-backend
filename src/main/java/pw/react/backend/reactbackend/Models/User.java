package pw.react.backend.reactbackend.Models;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;



@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable=false)
    private String FirstName;

    @Column(nullable=false)
    private String LastName;

    private Date BirthDate;

    @Column(nullable=false)
    private String Login;

    @Column(nullable=false)
    private boolean IsActive;



    public Long GetId() {
        return Id;
    }
    public  void  SetId (long id)
    {
        Id = id;
    }
    public String GetFirstName() {
        return FirstName;
    }
    public void SetFirstName(String firstName) {
        FirstName = firstName;
    }
    public String GetLastName() {
        return LastName;
    }
    public void SetLastName(String lastName) {
        this.LastName = lastName;
    }

    public Date GetBirthDate() {
        return BirthDate;
    }

    public void SetBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }

    public String GetLogin() {
        return Login;
    }

    public void SetLogin(String login) {
        Login = login;
    }

    public boolean GetIsActive() {
        return IsActive;
    }
    public void SetIsActive(boolean isActive)
    {
        IsActive = isActive;
    }
}