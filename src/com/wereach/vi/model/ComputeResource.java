package com.wereach.vi.model;

import java.util.ArrayList;

public class ComputeResource extends ManagedEntity {
	protected String name;
	protected ArrayList<ResourcePool> resources;
	protected ArrayList<HostSystem> hosts;
	//For class discriminator
	protected String type = "ComputeResource";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ResourcePool> getResources() {
		return resources;
	}

	public void setResources(ArrayList<ResourcePool> resources) {
		this.resources = resources;
	}

	public ArrayList<HostSystem> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<HostSystem> hosts) {
		this.hosts = hosts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
