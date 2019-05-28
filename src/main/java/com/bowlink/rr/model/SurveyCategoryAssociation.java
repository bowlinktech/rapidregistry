package com.bowlink.rr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author gchan
 */
@Entity
@Table(name = "survey_category_assoc")
public class SurveyCategoryAssociation {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "programId", nullable = false)
    private int programId;

    @Column(name = "surveyId", nullable = false)
    private int surveyId;
    
    @Column(name = "categoryId", nullable = false)
    private int categoryId;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "dateCreated", nullable = true)
    private Date dateCreated = new Date();

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

    public int getSurveyId() {
	return surveyId;
    }

    public void setSurveyId(int surveyId) {
	this.surveyId = surveyId;
    }

    public int getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
    }

    public Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
    }
    
    
    
}
