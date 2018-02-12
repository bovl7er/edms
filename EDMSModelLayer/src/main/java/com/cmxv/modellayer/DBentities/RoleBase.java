package com.cmxv.modellayer.DBentities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.cmxv.modellayer.abstracts.AbstractRole;
/**
 * 
 * Базовый класс ролей, содержит только поля из таблицы бд.
 * @author VLAD
 *
 */
@Entity
@Table(name="ROLES")
@XmlRootElement
public class RoleBase extends AbstractRole {

	

}
