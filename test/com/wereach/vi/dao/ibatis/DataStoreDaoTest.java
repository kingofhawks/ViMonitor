package com.wereach.vi.dao.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.wereach.vi.discover.impl.ViDiscoverImpl;
import com.wereach.vi.model.DataStore;
import com.wereach.vi.model.HostSystem;


public class DataStoreDaoTest {
	private static Logger logger = Logger.getLogger(DataStoreDaoTest.class);
	private DataStoreDaoImpl dao;
	
	@Before
	public void setup(){
		dao = new DataStoreDaoImpl();
	}
	@Test
	public void testInsert(){
		DataStore store = new DataStore();
		store.setName("ds-22");
		store.setUrl("http://test222");
		dao.saveOrUpdate(store);
		System.out.println("*****result ID*****"+store.getId());
	}
	
	@Test
	public void testSelectAll(){
		List<DataStore> stores = this.dao.getAllStores();
		System.out.println("**********"+stores.size());
	}
	
	@Test
	public void testSelect(){
		DataStore host = this.dao.getDataStoreById(14);
		System.out.println("**********"+host.getName());
	}
	
	@Test
	public void testSelectByHost(){
		List<DataStore> stores = this.dao.getDataStoreByHost("h2");
		System.out.println("**********"+stores.size());
	}
}
