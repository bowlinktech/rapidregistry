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
@Table(name = "survey_questions")
public class SurveyQuestions {

    @Transient
    List<SurveyQuestionChoices> questionChoices;
    
    @Transient
    private Integer pageNum = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "surveyId", nullable = false)
    private int surveyId = 0;

    @Column(name = "hide", nullable = false)
    private boolean hide = false;

    @Column(name = "required", nullable = false)
    private boolean required = false;
    
    @Column(name = "requiredResponse", nullable = false)
    private String  requiredResponse = "This question requires an answer";

    /**
     * display question text *
     */
    @Column(name = "dspQuestionId", nullable = true)
    private String dspQuestionId = "";

    @NotEmpty
    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "surveyPageId", nullable = false)
    private int surveyPageId = 0;

    @Column(name = "answerTypeId", nullable = false)
    private int answerTypeId = 0;

    @Column(name = "columnsDisplayed", nullable = false)
    private int columnsDisplayed = 0;

    @Column(name = "allowMultipleAns", nullable = false)
    private boolean allowMultipleAns = false;

    @Column(name = "saveToFieldId", nullable = false)
    private int saveToFieldId = 0;

    @Column(name = "autoPopulateFromField", nullable = false)
    private boolean autoPopulateFromField = false;

    @Column(name = "questionNum", nullable = false)
    private int questionNum = 1;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @Column(name = "validationId", nullable = false)
    private int validationId = 1;
    
    @Column(name = "alphabeticallySort", nullable = false)
    private boolean alphabeticallySort = false;
    
    @Column(name = "choiceLayout", nullable = true)
    private String choiceLayout = "1 Column";
    
    @Column(name = "populateFromTable", nullable = true)
    private String populateFromTable = "";
    
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
    
    @Column(name = "otherOption", nullable = true)
    private boolean otherOption = false;
    
    @Column(name = "otherLabel", nullable = true)
    private String otherLabel = "Other (please specify)";
    
    @Column(name = "otherDspChoice", nullable = true)
    private int otherDspChoice = 0;
    
    @Column(name = "dateFormatType", nullable = true)
    private int dateFormatType = 0;
    
    @Column(name = "includeTime", nullable = true)
    private boolean includeTime = false;
    
    @Column(name = "dateType", nullable = true)
    private int dateType = 1;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
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

    public List<SurveyQuestionChoices> getquestionChoices() {
        return questionChoices;
    }

    public void setquestionChoices(List<SurveyQuestionChoices> questionChoices) {
        this.questionChoices = questionChoices;
    }

    public String getRequiredResponse() {
        return requiredResponse;
    }

    public void setRequiredResponse(String requiredResponse) {
        this.requiredResponse = requiredResponse;
    }

    public int getValidationId() {
        return validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public boolean isAlphabeticallySort() {
        return alphabeticallySort;
    }

    public void setAlphabeticallySort(boolean alphabeticallySort) {
        this.alphabeticallySort = alphabeticallySort;
    }

    public String getChoiceLayout() {
        return choiceLayout;
    }

    public void setChoiceLayout(String choiceLayout) {
        this.choiceLayout = choiceLayout;
    }

    public String getPopulateFromTable() {
        return populateFromTable;
    }

    public void setPopulateFromTable(String populateFromTable) {
        this.populateFromTable = populateFromTable;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isOtherOption() {
        return otherOption;
    }

    public void setOtherOption(boolean otherOption) {
        this.otherOption = otherOption;
    }

    public String getOtherLabel() {
        return otherLabel;
    }

    public void setOtherLabel(String otherLabel) {
        this.otherLabel = otherLabel;
    }

    public int getOtherDspChoice() {
        return otherDspChoice;
    }

    public void setOtherDspChoice(int otherDspChoice) {
        this.otherDspChoice = otherDspChoice;
    }

    public int getDateFormatType() {
        return dateFormatType;
    }

    public void setDateFormatType(int dateFormatType) {
        this.dateFormatType = dateFormatType;
    }

    public boolean isIncludeTime() {
        return includeTime;
    }

    public void setIncludeTime(boolean includeTime) {
        this.includeTime = includeTime;
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }
    
    
}
