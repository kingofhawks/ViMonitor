package com.wereach.vi.discover;

import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetricBase;
import com.wereach.vi.model.ClusterComputeResource;
import com.wereach.vi.model.DataCenter;
import com.wereach.vi.model.DataStore;
import com.wereach.vi.model.HostSystem;
import com.wereach.vi.model.VirtualMachine;


public interface ViDiscoverInterface {
	
	/* to start the 
	on demand inventory discovery, store inventory in NMS memory 
	cache and DB */
	public void startDiscovery();	
	
	/* to start the 
	on demand inventory discovery, store inventory in NMS memory 
	cache and DB */ 
	public void startDiscovery(String dataCenter); 
	
	public void unmanageDataCenter(String dataCenter); /* to remove 
	all inventory objects stored in NMS memory cache */ 
	
	public DataCenter getAllInventory(String dataCenter);
	/* to return all inventory from NMS DB for the DataCenter. 
	Please define the return type, something like a tree of 
	ManagedEntity */ 
	
	public HostSystem[] getAllHosts();
	
	public HostSystem[] getHostByDataCenter(String dataCenter);
	
	public VirtualMachine[] getAllVMs(String dataCenter); 
	 
	public VirtualMachine[] getVMByHost(String hostName); 
	 
	public ClusterComputeResource[] getAllClusters(); 
	 
	public HostSystem[] getHostByCluster(String clusterName); 	 
	 
	public DataStore[] getAllDataStores(String dataCenter); 
	 
	public DataStore[] getDataStoreByHost(String host); 
	
	public PerfCounterInfo[] getCounters(String host); 
	 
	public PerfCounterInfo[] getCountersByVm(String vm); 
	 
	public PerfEntityMetricBase[] getPMDataRealTime(String host); 
	 
	public PerfEntityMetricBase[] getPMDataHistory(String host, int interval, int startTime, int duration, String groupName, String counterName); 
	 
	public PerfEntityMetricBase[] getPMDataRealTimeByVm(String vm); 
	
	public PerfEntityMetricBase[] getPMDataHistoryByVm(String vm, int interval, int startTime, int duration, String groupName, String counterName);
}
