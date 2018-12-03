package com.ruralhousejsf.domain;

import java.io.Serializable;

//@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class City implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	@XmlID
//	@XmlJavaTypeAdapter(IntegerAdapter.class)
//	@Id
//	@GeneratedValue
	private int id;
	private String name;

	public City(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
