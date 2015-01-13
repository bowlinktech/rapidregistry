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
    
    @NotEmpty
    @Column(name = "questionId", nullable = false)
    private int questionId = 0;
    
    @NotEmpty
    @Column(name = "skipToPageId", nullable = true)
    private int skipToPageId = 0;
    
    @NotEmpty
    @Column(name = "skipToQuestionId", nullable = true)
    private int skipToQuestionId = 0;
    
    /** label is used for textboxes **/
    @NotEmpty
    @Column(name = "answerOrLabel", nullable = false)
    private String answerOrLabel;
    
    @NotEmpty
    @Column(name = "activityCodeId", nullable = true)
    private int activityCodeId = 0;
    
    @Column(name = "defAnswer", nullable = false)
    private boolean defAnswer = false;
    
    @Column(name = "defAnswerText", nullable = true)
    private String defAnswerText;
    
    /** this orders the answers **/
    @NotEmpty
    @Column(name = "answerNum", nullable = true)
    private int answerNum = 1;
    
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

	public String getDefAnswerText() {
		return defAnswerText;
	}

	public void setDefAnswerText(String defAnswerText) {
		this.defAnswerText = defAnswerText;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(int answerNum) {
		this.answerNum = answerNum;
	}
	
}
