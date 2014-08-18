package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USERS")
public class User {
    
    @Transient
    Long timesloggedIn = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "STATUS", nullable = false)
    private boolean status = false;

    @NotEmpty
    @NoHtml
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @NotEmpty
    @NoHtml
    @Column(name = "LASTNAME", nullable = true)
    private String lastName;

    @NotEmpty
    @NoHtml
    @Size(min = 4, max = 15)
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @NotEmpty
    @NoHtml
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    
    @Email
    @NoHtml
    @Column(name = "EMAIL", nullable = false)
    private String email;
    
    @Column(name = "ROLEID", nullable = false)
    private int roleId = 2;

    @Column(name = "CREATEDBY", nullable = false)
    private int createdBy;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @NoHtml
    @Column(name = "RESETCODE", nullable = true)
    private String resetCode = null;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getresetCode() {
        return resetCode;
    }
    
    public void setresetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public Long getTimesloggedIn() {
        return timesloggedIn;
    }

    public void setTimesloggedIn(Long timesloggedIn) {
        this.timesloggedIn = timesloggedIn;
    }
    
    
   
}
