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

import com.bowlink.rr.validator.NoHtml;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "survey_pages")
public class SurveyPages {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @NoHtml
    @Column(name = "pageTitle", nullable = false)
    private String pageTitle;
    
    @NotEmpty
    @Column(name = "surveyId", nullable = false)
    private int surveyId;
    
    @NotEmpty
    @Column(name = "pageNum", nullable = false)
    private int pageNum;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @Transient List <SurveyQuestions> surveyQuestions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<SurveyQuestions> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(List<SurveyQuestions> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
  
}
