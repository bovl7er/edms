package com.cmxv.modellayer.DBentities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cmxv.modellayer.abstracts.AbstractRole;

/**
 * Расширение класса RoleBase, содержит необходимые связи ролей с другими сущностями.
 * @author VLAD
 *
 */
@Entity
@Table(name="ROLES")
public class RoleExt extends AbstractRole {

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ROLES_RIGHTS",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "RIGHT_ID"))
    private List<Right> rights;
	
    public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}
    
}
