package com.wereach.vi.model;

import java.util.ArrayList;

public class DataStore extends ManagedEntity {
	private ArrayList<HostSystem> hosts;
	private ArrayList<VirtualMachine> vms;
	private long freeSpace;
	private long maxFileSize;
	private String name;
	private String url;
	private DataCenter dc;
	private int dcId;
	private String host;

	public ArrayList<HostSystem> getHosts() {
		return hosts;
	}

	public void setHosts(ArrayList<HostSystem> hosts) {
		this.hosts = hosts;
	}

	public ArrayList<VirtualMachine> getVms() {
		return vms;
	}

	public void setVms(ArrayList<VirtualMachine> vms) {
		this.vms = vms;
	}

	public long getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(long freeSpace) {
		this.freeSpace = freeSpace;
	}

	public long getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
