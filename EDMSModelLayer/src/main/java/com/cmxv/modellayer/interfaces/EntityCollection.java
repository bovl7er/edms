package com.cmxv.modellayer.interfaces;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EntityCollection <T> {
	private Collection<T> collection;

	
	
	@XmlElement(name = "elements")
	public Collection<T> getCollection() {
		return collection;
	}

	public void setCollection(Collection<T> collection) {
		this.collection = collection;
	}
}
