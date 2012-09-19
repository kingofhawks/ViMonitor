package com.wereach.vi.dao;

import java.util.List;

import com.wereach.vi.model.ClusterComputeResource;
import com.wereach.vi.model.ComputeResource;


public interface ComputeResourceDao {
	public ComputeResource getComputeResourceById(int id);
	
	public ComputeResource getComputeResourceByName(String name);
	
	public void createComputeResource(ComputeResource ccr);	
	
	public List<ComputeResource> getAllComputeResources();
	
	public List<ClusterComputeResource> getAllClusterComputeResources();
	
	public int updateComputeResource(ComputeResource ccr);
	
	public void saveOrUpdate(ComputeResource ccr);
}
