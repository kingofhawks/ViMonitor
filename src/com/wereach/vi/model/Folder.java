package com.wereach.vi.model;

import java.util.ArrayList;

public class Folder extends ManagedEntity {
	public static final String VM_FOLDER = "vmFolder";
	public static final String HOST_FOLDER = "hostFolder";
	private String name;
	private String childType;//vmFolder or hostFolder
	private ArrayList<ManagedEntity> children;
	private DataCenter dc;
	private int dcId;
	
	public String getChildType() {
		return childType;
	}
	public void setChildType(String childType) {
		this.childType = childType;
	}
	public DataCenter getDc() {
		return dc;
	}
	public void setDc(DataCenter dc) {
		this.dc = dc;
	}
	public int getDcId() {
		return dcId;
	}
	public void setDcId(int dcId) {
		this.dcId = dcId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ManagedEntity> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<ManagedEntity> children) {
		this.children = children;
	} 
	
}
