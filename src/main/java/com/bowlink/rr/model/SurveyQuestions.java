package com.bowlink.rr.model;

import java.util.Date;

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
@Table(name = "survey_questions")
public class SurveyQuestions {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @NotEmpty
    @Column(name = "hide", nullable = false)
    private boolean hide = false;
    
    @NotEmpty
    @Column(name = "required", nullable = false)
    private boolean required = false;
    
    /** display question text **/
    @Column(name = "dspQuestionId", nullable = true)
    private String dspQuestionId;
    
    @NotEmpty
    @Column(name = "question", nullable = false)
    private String question;

    @NotEmpty
    @Column(name = "surveyPageId", nullable = false)
    private int surveyPageId = 0;
    
    @NotEmpty
    @Column(name = "answerTypeId", nullable = false)
    private int answerTypeId = 0;
    
    @NotEmpty
    @Column(name = "columnsDisplayed", nullable = false)
    private int columnsDisplayed = 0;
    
    @NotEmpty
    @Column(name = "allowMultipleAns", nullable = false)
    private boolean allowMultipleAns = false;
    
    @NoHtml
    @Column(name = "saveToTableName", nullable = true)
    private String saveToTableName;

    @NotEmpty
    @Column(name = "saveToFieldId", nullable = false)
    private int saveToFieldId = 0;
    
    @NotEmpty
    @Column(name = "autoPopulateFromField", nullable = false)
    private boolean autoPopulateFromField = false;
    
    @NotEmpty
    @Column(name = "questionNum", nullable = false)
    private int questionNum = 1;
    
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getDspQuestionId() {
		return dspQuestionId;
	}

	public void setDspQuestionId(String dspQuestionId) {
		this.dspQuestionId = dspQuestionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getSurveyPageId() {
		return surveyPageId;
	}

	public void setSurveyPageId(int surveyPageId) {
		this.surveyPageId = surveyPageId;
	}

	public int getAnswerTypeId() {
		return answerTypeId;
	}

	public void setAnswerTypeId(int answerTypeId) {
		this.answerTypeId = answerTypeId;
	}

	public int getColumnsDisplayed() {
		return columnsDisplayed;
	}

	public void setColumnsDisplayed(int columnsDisplayed) {
		this.columnsDisplayed = columnsDisplayed;
	}

	public boolean isAllowMultipleAns() {
		return allowMultipleAns;
	}

	public void setAllowMultipleAns(boolean allowMultipleAns) {
		this.allowMultipleAns = allowMultipleAns;
	}

	public String getSaveToTableName() {
		return saveToTableName;
	}

	public void setSaveToTableName(String saveToTableName) {
		this.saveToTableName = saveToTableName;
	}

	public int getSaveToFieldId() {
		return saveToFieldId;
	}

	public void setSaveToFieldId(int saveToFieldId) {
		this.saveToFieldId = saveToFieldId;
	}

	public boolean isAutoPopulateFromField() {
		return autoPopulateFromField;
	}

	public void setAutoPopulateFromField(boolean autoPopulateFromField) {
		this.autoPopulateFromField = autoPopulateFromField;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}	
    
}
