package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USERS")
public class User {

    @Transient
    private Long timesloggedIn = null;

    @Transient
    private String staffType = null;

    @Transient
    private List<String> registries = null;

    @Transient
    private String encryptedId = null;

    @Transient
    private String encryptedSecret = null;
    
    @Transient
    private String encryptedUserName = null;

    @Transient
    private String password;

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
    @Email
    @NoHtml
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "TYPEID", nullable = false)
    private Integer typeId = 0;

    @Column(name = "ROLEID", nullable = false)
    private int roleId = 2;

    @Column(name = "CREATEDBY", nullable = false)
    private int createdBy = 1;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

    @NoHtml
    @Column(name = "RESETCODE", nullable = true)
    private String resetCode = null;

    @Column(name = "LASTLOGGEDIN", nullable = true)
    private Date lastloggedIn = null;

    @Column(name = "randomSalt", nullable = true)
    private byte[] randomSalt;

    @Column(name = "encryptedPw", nullable = true)
    private byte[] encryptedPw;
    
    @NotEmpty
    @NoHtml
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "private", nullable = false)
    private boolean privateProfile = false;
    
    @NoHtml
    @Column(name = "phonenumber", nullable = true)
    private String phoneNumber = null;

    public byte[] getRandomSalt() {
        return randomSalt;
    }

    public void setRandomSalt(byte[] randomSalt) {
        this.randomSalt = randomSalt;
    }

    public byte[] getEncryptedPw() {
        return encryptedPw;
    }

    public void setEncryptedPw(byte[] encryptedPw) {
        this.encryptedPw = encryptedPw;
    }
    
    @NoHtml
    @Column(name = "profilePhoto", nullable = false)
    private String profilePhoto;
    

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public List<String> getRegistries() {
        return registries;
    }

    public void setRegistries(List<String> registries) {
        this.registries = registries;
    }

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getEncryptedSecret() {
        return encryptedSecret;
    }

    public void setEncryptedSecret(String encryptedSecret) {
        this.encryptedSecret = encryptedSecret;
    }

    public Date getLastloggedIn() {
        return lastloggedIn;
    }

    public void setLastloggedIn(Date lastloggedIn) {
        this.lastloggedIn = lastloggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getEncryptedUserName() {
		return encryptedUserName;
	}

	public void setEncryptedUserName(String encryptedUserName) {
		this.encryptedUserName = encryptedUserName;
	}

	public String getResetCode() {
		return resetCode;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public boolean isPrivate() {
		return privateProfile;
	}

	public void setPrivate(boolean privateProfile) {
		this.privateProfile = privateProfile;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
