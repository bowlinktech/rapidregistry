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
public class algorithmCategories {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @NoHtml
    @Column(name = "mapToTable", nullable = false)
    private String mapToTable;
    
    @NotEmpty
    @NoHtml
    @Column(name = "mapToColumn", nullable = false)
    private String mapToColumn;

    @NotEmpty
    @NoHtml
    @Column(name = "displayName", nullable = false)
    private String displayName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMapToTable() {
		return mapToTable;
	}

	public void setMapToTable(String mapToTable) {
		this.mapToTable = mapToTable;
	}

	public String getMapToColumn() {
		return mapToColumn;
	}

	public void setMapToColumn(String mapToColumn) {
		this.mapToColumn = mapToColumn;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
      
}
