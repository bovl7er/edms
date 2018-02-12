package com.cmxv.modellayer.DBentities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

import com.cmxv.modellayer.abstracts.AbstractUser;

@Entity
@Table(name = "USERS")
@XmlType
public class UserWithRights extends AbstractUser{
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<RoleExt> roles;

	public List<RoleExt> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleExt> roles) {
		this.roles = roles;
	}
}
