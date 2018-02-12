package com.cmxv.modellayer.abstracts;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public abstract class AbstractRole {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "ROLE_SEQ",allocationSize = 1)
    @Column(name="ROLE_ID")
    private Integer roleId;
    
    @Column(name="ROLE_NAME")
    private String roleName;
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Override
    public boolean equals(Object obj) {
    	
    	return (obj instanceof AbstractRole) && (((AbstractRole) obj).getRoleId() == this.roleId);
    }
}
