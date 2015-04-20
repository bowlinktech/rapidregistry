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
@Table(name = "lu_delimiters")
public class delimiters {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @NoHtml
    @Column(name = "delimiter", nullable = false)
    private String delimiter;
    
    @NotEmpty
    @NoHtml
    @Column(name = "delimChar", nullable = false)
    private String delimChar;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getDelimChar() {
		return delimChar;
	}

	public void setDelimChar(String delimChar) {
		this.delimChar = delimChar;
	}
    
}
