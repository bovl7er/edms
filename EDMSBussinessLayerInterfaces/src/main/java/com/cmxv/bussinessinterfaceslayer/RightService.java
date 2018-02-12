package com.cmxv.bussinessinterfaceslayer;

import java.util.List;

import com.cmxv.modellayer.DBentities.Right;

public interface RightService {
	public List<Right> getAllRights();
	
	public Right getRightById(int rightId);
}
