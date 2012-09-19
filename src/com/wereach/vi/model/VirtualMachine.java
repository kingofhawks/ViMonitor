package com.wereach.vi.model;
public class VirtualMachine extends ManagedEntity {
	private String name;
	private long memorySize;
	private int cpuCount;
	private HostSystem host;
	private int hostId;
	private String ostype = "";
	
	public VirtualMachine() {
		super();
	}
	public VirtualMachine(String name, long memorySize, int cpuCount,
			String ostype) {
		super();
		this.name = name;
		this.memorySize = memorySize;
		this.cpuCount = cpuCount;
		this.ostype = ostype;
	}
	public VirtualMachine(String name, long memorySize, int cpuCount,
			int hostId, String ostype) {
		super();
		this.name = name;
		this.memorySize = memorySize;
		this.cpuCount = cpuCount;
		this.hostId = hostId;
		this.ostype = ostype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void setCpuCount(short cpuCount) {
		this.cpuCount = cpuCount;
	}
	public HostSystem getHost() {
		return host;
	}
	public void setHost(HostSystem host) {
		this.host = host;
	}
	public String getOstype() {
		return ostype;
	}
	public void setOstype(String ostype) {
		this.ostype = ostype;
	}
	public int getHostId() {
		return hostId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	
}
