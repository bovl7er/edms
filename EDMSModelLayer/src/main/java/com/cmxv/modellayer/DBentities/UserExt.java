package com.cmxv.modellayer.DBentities;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.Hibernate;

import com.cmxv.modellayer.abstracts.AbstractUser;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
@XmlRootElement
public class UserExt extends AbstractUser implements Serializable {
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))

    private List<RoleBase> roles;
    
	@XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentUser")
    private List<DocumentBase> documents;
    
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentAuthor")
    private List<DocumentBase> createdDocuments;
    
    public List<RoleBase> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleBase> roles) {
        this.roles = roles;
    }

	@XmlTransient
    public List<DocumentBase> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentBase> documents) {
        this.documents = documents;
    }
    
    @XmlTransient
    public List<DocumentBase> getCreatedDocuments() {
        return createdDocuments;
    }

    public void setCreatedDocuments(List<DocumentBase> createdDocuments) {
        this.createdDocuments = createdDocuments;
    }
}
