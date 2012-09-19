package com.wereach.vi.dao.ibatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.wereach.vi.discover.impl.ViDiscoverImpl;
import com.wereach.vi.model.HostSystem;


public class HostSystemDaoTest {
	private static Logger logger = Logger.getLogger(HostSystemDaoTest.class);
	private HostSystemDaoImpl dao;
	
	@Before
	public void setup(){
		dao = new HostSystemDaoImpl();
	}
	@Test
	public void testInsert(){
		/*for (int i =1;i<=10000;i++){
			HostSystem host = new HostSystem();
			host.setHostName("host"+i);
			dao.createHostSystem(host);
		}*/
		HostSystem host = new HostSystem();
		host.setHostName("Host1-22");
		host.setVendor("v1");
		host.setDcId(1);
		dao.saveOrUpdate(host);
		System.out.println("*****result ID*****"+host.getId());
	}
	
	@Test
	public void testSelectAll(){
		long begin = System.currentTimeMillis();
		dao.getAllHosts();
		long end = System.currentTimeMillis();
		logger.debug("*****time1###"+(end-begin));		
	}
	
	@Test
	public void testSelect(){
		HostSystem host = this.dao.getHostSystemById(1);
		System.out.println("**********"+host.getVms().size());
	}
	
	@Test
	public void testSelectByName(){
		HostSystem host = this.dao.getHostSystemByName("Host1-22");
		System.out.println("****host****"+host);
		System.out.println("****ID"+host.getId()+"**********"+host.getVms().size());
		System.out.println("****ID***"+host.getVms().get(0).getName());
	}
	
	@Test
	public void testSelectByDataCenter(){
		List<HostSystem> hosts = this.dao.getHostsByDataCenter("dc1");
		System.out.println("****host****"+hosts.size());		
	}
	
	@Test
	public void testGetByDataCenter(){
		ViDiscoverImpl discover = new ViDiscoverImpl();
		HostSystem[] hosts = discover.getHostByDataCenter("dc1");
		System.out.println("****hosts****"+hosts.length);		
	}
}
