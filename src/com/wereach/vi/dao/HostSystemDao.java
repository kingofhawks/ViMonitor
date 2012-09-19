package com.wereach.vi.dao;

import java.util.List;

import com.wereach.vi.model.HostSystem;

public interface HostSystemDao {
	public HostSystem getHostSystemById(int id);
	
	public HostSystem getHostSystemByName(String name);
	
	public int createHostSystem(HostSystem host);
	
	public int updateHostSystem(HostSystem host);
	
	public void saveOrUpdate(HostSystem host);
	
	public List<HostSystem> getAllHosts();
	
	public List<HostSystem> getHostsByDataCenter(String dataCenter);
}
