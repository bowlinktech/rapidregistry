package com.ut.dph.model;

import com.ut.dph.validator.NoHtml;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rel_transportWebServiceDetails")
public class configurationWebServiceFields {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "TRANSPORTID", nullable = false)
    private int transportId = 0;

    @NoHtml
    @Column(name = "domain", nullable = true)
    private String domain = null;

    /** 1 = toUT\n2 = fromUT **/
    @Column(name = "Method", nullable = true)
    private int method = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransportId() {
		return transportId;
	}

	public void setTransportId(int transportId) {
		this.transportId = transportId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}
    
	
}
