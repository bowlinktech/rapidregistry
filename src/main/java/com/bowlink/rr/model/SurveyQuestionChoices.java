package com.bowlink.rr.model;

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
@Table(name = "survey_questionChoices")
public class SurveyQuestionChoices {

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
    @Column(name = "choiceText", nullable = false)
    private String choiceText;

    @Column(name = "activityCodeId", nullable = true)
    private int activityCodeId = 0;

    @Column(name = "defAnswer", nullable = false)
    private boolean defAnswer = false;
    
    @Column(name = "choiceValue", nullable = false)
    private int choiceValue = 0;
    
    @Column(name = "skipToEnd", nullable = false)
    private boolean skipToEnd = false;
    
    @Column(name = "answerNum", nullable = false)
    private int answerNum = 0;
    
    @Column(name = "answerScore", nullable = false)
    private int answerScore = 0;

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

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public int getChoiceValue() {
        return choiceValue;
    }

    public void setChoiceValue(int choiceValue) {
        this.choiceValue = choiceValue;
    }

    public boolean isSkipToEnd() {
        return skipToEnd;
    }

    public void setSkipToEnd(boolean skipToEnd) {
        this.skipToEnd = skipToEnd;
    }

    public int getAnswerNum() {
	return answerNum;
    }

    public void setAnswerNum(int answerNum) {
	this.answerNum = answerNum;
    }

    public int getAnswerScore() {
	return answerScore;
    }

    public void setAnswerScore(int answerScore) {
	this.answerScore = answerScore;
    }
    
    
}
