package com.wereach.vi.dao.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.wereach.vi.discover.impl.ViDiscoverImpl;
import com.wereach.vi.model.ClusterComputeResource;
import com.wereach.vi.model.ComputeResource;
import com.wereach.vi.model.DataCenter;
import com.wereach.vi.model.DataStore;
import com.wereach.vi.model.HostSystem;


public class ComputeResourceDaoTest {
	private static Logger logger = Logger.getLogger(ComputeResourceDaoTest.class);
	private ComputeResourceDaoImpl dao;
	
	@Before
	public void setup(){
		dao = new ComputeResourceDaoImpl();
	}
	@Test
	public void testInsert(){
		ComputeResource store = new ComputeResource();
		store.setName("cr1");
		dao.saveOrUpdate(store);
		System.out.println("*****result ID*****"+store.getId());
	}
	
	@Test
	public void testSelectAll(){
		List<ComputeResource> stores = this.dao.getAllComputeResources();
		System.out.println("**********"+stores.size());
	}
	
	@Test
	public void testSelectAllCluster(){
		List<ClusterComputeResource> stores = this.dao.getAllClusterComputeResources();
		System.out.println("**********cluster***"+stores.size());
		System.out.println("**********cluster***"+stores.get(0).getName());
	}
	
	@Test
	public void testSelect(){
		ComputeResource host = this.dao.getComputeResourceById(1);
		System.out.println("**********"+host.getName());
	}
	
	@Test
	public void testSelectByName(){
		ComputeResource host = this.dao.getComputeResourceByName("cr1");
		System.out.println("******host***"+host);
		System.out.println(host+"**********"+host.getName()+"***"+host.getHosts().get(0).getHostName());
	}
}
