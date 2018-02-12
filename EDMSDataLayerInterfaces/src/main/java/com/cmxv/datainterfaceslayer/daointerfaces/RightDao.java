package com.cmxv.datainterfaceslayer.daointerfaces;

import java.util.List;

import com.cmxv.modellayer.DBentities.Right;

public interface RightDao {
	public List<Right> getAllRights();
	
	public Right getRightById(int rightId);

}
