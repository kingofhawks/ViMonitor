package com.wereach.vi.dao.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.wereach.vi.discover.impl.ViDiscoverImpl;
import com.wereach.vi.model.DataCenter;
import com.wereach.vi.model.DataStore;
import com.wereach.vi.model.HostSystem;


public class DataCenterDaoTest {
	private static Logger logger = Logger.getLogger(DataCenterDaoTest.class);
	private DataCenterDaoImpl dao;
	
	@Before
	public void setup(){
		dao = new DataCenterDaoImpl();
	}
	@Test
	public void testInsert(){
		DataCenter store = new DataCenter();
		store.setName("ds-22");
		dao.saveOrUpdate(store);
		System.out.println("*****result ID*****"+store.getId());
	}
	
	@Test
	public void testSelectAll(){
		List<DataCenter> stores = this.dao.getAllDataCenters();
		System.out.println("**********"+stores.size());
	}
	
	@Test
	public void testSelect(){
		DataCenter host = this.dao.getDataCenterById(1);
		System.out.println("**********"+host.getName());		
	}
	
	@Test
	public void testSelectByName(){
		DataCenter host = this.dao.getDataCenterByName("dc1");
		System.out.println(host+"**********");
		System.out.println(host+"**********"+host.getName()+host.getDataStores().get(0).getName());
	}
}
