package com.wereach.vi.model;

import java.util.ArrayList;

public class ResourcePool extends ManagedEntity {
	private String name;
	private ArrayList<VirtualMachine> vms;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<VirtualMachine> getVms() {
		return vms;
	}

	public void setVms(ArrayList<VirtualMachine> vms) {
		this.vms = vms;
	}

}
