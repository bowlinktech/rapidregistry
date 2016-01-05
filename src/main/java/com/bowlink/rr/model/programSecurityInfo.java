package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "programSecurityInfo")
public class programSecurityInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @NoHtml
    @Column(name = "encryptedPhrase", nullable = false)
    private String encryptedPhrase;
    
    @Column(name = "programId", nullable = false)
    private Integer programId = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEncryptedPhrase() {
		return encryptedPhrase;
	}

	public void setEncryptedPhrase(String encryptedPhrase) {
		this.encryptedPhrase = encryptedPhrase;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

}
