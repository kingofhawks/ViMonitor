package com.wereach.vi.dao.ibatis;

import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.wereach.vi.model.Folder;


public class FolderDaoTest {
	private static Logger logger = Logger.getLogger(FolderDaoTest.class);
	private FolderDaoImpl dao;
	
	@Before
	public void setup(){
		dao = new FolderDaoImpl();
	}
	@Test
	public void testInsert(){
		Folder store = new Folder();
		store.setName("f1");
		store.setChildType("vmFolder");
		store.setDcId(1);
		dao.saveOrUpdate(store);
		System.out.println("*****result ID*****"+store.getId());
	}
	
	@Test
	public void testSelectAll(){
		List<Folder> stores = this.dao.getAllFolders();
		System.out.println("**********"+stores.size());
	}
	
	@Test
	public void testSelect(){
		Folder host = this.dao.getFolderById(2);
		System.out.println("**********"+host.getName());
	}
	
	@Test
	public void testSelectByName(){
		Folder host = this.dao.getFolderByName("f1");
		System.out.println(host+"**********"+host.getDc().getName());
		
	}
}
