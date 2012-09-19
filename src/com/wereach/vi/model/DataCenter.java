package com.wereach.vi.model;

import java.util.ArrayList;
import java.util.List;

public class DataCenter extends ManagedEntity {
	private String name;
	private ArrayList<Folder> folders;
	private Folder vmFolder;
	private Folder hostFolder;
	private List<DataStore> dataStores;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Folder> getFloders() {
		return folders;
	}

	public void setFloders(ArrayList<Folder> floders) {
		this.folders = floders;
	}

	public Folder getVmFolder() {
		return vmFolder;
	}

	public void setVmFolder(Folder vmFolder) {
		this.vmFolder = vmFolder;
	}

	public Folder getHostFolder() {
		return hostFolder;
	}

	public void setHostFolder(Folder hostFolder) {
		this.hostFolder = hostFolder;
	}

	public List<DataStore> getDataStores() {
		return dataStores;
	}

	public void setDataStores(List<DataStore> dataStores) {
		this.dataStores = dataStores;
	}

}
