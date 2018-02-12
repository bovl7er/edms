package com.cmxv.modellayer.DBentities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.DiscriminatorFormula;

import com.cmxv.modellayer.abstracts.AbstractUser;



@Entity
@Table(name = "USERS")
@XmlRootElement
public class UserBase extends AbstractUser {
	
}
