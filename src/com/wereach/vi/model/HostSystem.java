package com.wereach.vi.model;

import java.util.ArrayList;

public class HostSystem extends ManagedEntity {
	private long[] nwwn;
	private String ostype = "";
	private String vendor = "";
	private String[] ipAddresses;
	private long memorySize;
	private int cpuCount;
	private long cpuSpeedAvg;
	private String hostName;
	private ArrayList<VirtualMachine> vms;
	private ComputeResource res;
	private int resId;
	private int dcId;

	public HostSystem() {
		super();
	}

	public HostSystem(String vendor, long memorySize, short cpuCount,
			long cpuSpeedAvg, String hostName) {
		super();
		this.vendor = vendor;
		this.memorySize = memorySize;
		this.cpuCount = cpuCount;
		this.cpuSpeedAvg = cpuSpeedAvg;
		this.hostName = hostName;
	}

	public HostSystem(long[] nwwn, String ostype, String vendor,
			String[] ipAddresses, long memorySize, short cpuCount,
			long cpuSpeedAvg, String hostName) {
		super();
		this.nwwn = nwwn;
		this.ostype = ostype;
		this.vendor = vendor;
		this.ipAddresses = ipAddresses;
		this.memorySize = memorySize;
		this.cpuCount = cpuCount;
		this.cpuSpeedAvg = cpuSpeedAvg;
		this.hostName = hostName;
	}

	public int getDcId() {
		return dcId;
	}

	public void setDcId(int dcId) {
		this.dcId = dcId;
	}

	public ComputeResource getRes() {
		return res;
	}

	public void setRes(ComputeResource res) {
		this.res = res;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public long[] getNwwn() {
		return nwwn;
	}

	public void setNwwn(long[] nwwn) {
		this.nwwn = nwwn;
	}

	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String[] getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(String[] ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public long getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(long memorySize) {
		this.memorySize = memorySize;
	}

	public int getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(int cpuCount) {
		this.cpuCount = cpuCount;
	}

	public long getCpuSpeedAvg() {
		return cpuSpeedAvg;
	}

	public void setCpuSpeedAvg(long cpuSpeedAvg) {
		this.cpuSpeedAvg = cpuSpeedAvg;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public ArrayList<VirtualMachine> getVms() {
		return vms;
	}

	public void setVms(ArrayList<VirtualMachine> vms) {
		this.vms = vms;
	}

}