package com.bowlink.rr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author gchan
 */
@Entity
@Table(name = "survey_answers")
public class SurveyAnswers {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "questionId", nullable = false)
    private int questionId = 0;
    
    @Column(name = "skipToPageId", nullable = true)
    private int skipToPageId = 0;
    
    @Column(name = "skipToQuestionId", nullable = true)
    private int skipToQuestionId = 0;
    
    @NotEmpty
    @Column(name = "answer", nullable = false)
    private String answer;
    
    /** label is used for textboxes **/
    @NotEmpty
    @Column(name = "label", nullable = false)
    private String label;
    
    @Column(name = "activityCodeId", nullable = true)
    private int activityCodeId = 0;
    
    @Column(name = "defAnswer", nullable = false)
    private boolean defAnswer = false;
    
    @Column(name = "defAnsText", nullable = true)
    private String defAnsText;
    
    @Column(name = "numericOnly", nullable = false)
    private boolean numericOnly = false;
    
    @Column(name = "maxLength", nullable = true)
    private int maxLength = 255;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getSkipToPageId() {
		return skipToPageId;
	}

	public void setSkipToPageId(int skipToPageId) {
		this.skipToPageId = skipToPageId;
	}

	public int getSkipToQuestionId() {
		return skipToQuestionId;
	}

	public void setSkipToQuestionId(int skipToQuestionId) {
		this.skipToQuestionId = skipToQuestionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getActivityCodeId() {
		return activityCodeId;
	}

	public void setActivityCodeId(int activityCodeId) {
		this.activityCodeId = activityCodeId;
	}

	public boolean isDefAnswer() {
		return defAnswer;
	}

	public void setDefAnswer(boolean defAnswer) {
		this.defAnswer = defAnswer;
	}

	public String getDefAnsText() {
		return defAnsText;
	}

	public void setDefAnsText(String defAnsText) {
		this.defAnsText = defAnsText;
	}

	public boolean isNumericOnly() {
		return numericOnly;
	}

	public void setNumericOnly(boolean numericOnly) {
		this.numericOnly = numericOnly;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
