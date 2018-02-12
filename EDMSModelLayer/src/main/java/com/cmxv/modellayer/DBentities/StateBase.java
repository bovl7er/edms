package com.cmxv.modellayer.DBentities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "STATES")
public class StateBase implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_id_seq")
    @SequenceGenerator(name = "state_id_seq", sequenceName = "STATE_SEQ", allocationSize = 1)
    @Column(name = "STATE_ID")
    private Integer stateId;

    @Column(name = "STATE_NAME")
    private String stateName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentState")
    private List<DocumentBase> documents;

    public StateBase() {
    }

    public StateBase(Integer stateId, String stateName) {
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public List<DocumentBase> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentBase> documents) {
        this.documents = documents;
    }

    
    @Override
    public String toString() {
        return "StateBase{" + "stateId=" + stateId + ", stateName=" + stateName + '}';
    }

}
