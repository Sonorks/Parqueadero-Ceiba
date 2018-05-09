package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Watchman {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String cc;
	private String name;
	private String lastname;
	
	public String getCc() {
		return cc;
	}
	public String getName() {
		return name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	
}
