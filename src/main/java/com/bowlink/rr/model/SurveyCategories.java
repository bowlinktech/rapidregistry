package com.bowlink.rr.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author gchan
 */
@Entity
@Table(name = "survey_categories")
public class SurveyCategories {

    @Transient
    List<surveys> surveys;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "programId", nullable = false)
    private int programId;

    @NotEmpty
    @Column(name = "categoryName", nullable = false)
    private String categoryName = "";
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "dateCreated", nullable = true)
    private Date dateCreated = new Date();

    public List<surveys> getSurveys() {
	return surveys;
    }

    public void setSurveys(List<surveys> surveys) {
	this.surveys = surveys;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getProgramId() {
	return programId;
    }

    public void setProgramId(int programId) {
	this.programId = programId;
    }

    public String getCategoryName() {
	return categoryName;
    }

    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }

    public Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
    }
    
    
}
