package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "rel_crosswalkData")
public class crosswalkData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @NotEmpty
    @NoHtml
    @Column(name = "crosswalkId", nullable = false)
    private int crosswalkId;

    @Column(name = "sourceValue", nullable = true)
    private String sourceValue;

    @NoHtml
    @Column(name = "targetValue", nullable = true)
    private String targetValue;

    
    @Column(name = "descValue", nullable = true)
    private String descValue;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getCrosswalkId() {
		return crosswalkId;
	}


	public void setCrosswalkId(int crosswalkId) {
		this.crosswalkId = crosswalkId;
	}


	public String getSourceValue() {
		return sourceValue;
	}


	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}


	public String getTargetValue() {
		return targetValue;
	}


	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}


	public String getDescValue() {
		return descValue;
	}


	public void setDescValue(String descValue) {
		this.descValue = descValue;
	}

}
