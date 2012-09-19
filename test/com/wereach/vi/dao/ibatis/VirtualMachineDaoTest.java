package com.wereach.vi.dao.ibatis;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import com.wereach.vi.model.VirtualMachine;


public class VirtualMachineDaoTest {
	private static Logger logger = Logger.getLogger(VirtualMachineDaoTest.class);
	private VirtualMachineDaoImpl dao;
	
	@Before
	public void setup(){
		dao = new VirtualMachineDaoImpl();
	}
	@Test
	public void testInsert(){
		VirtualMachine vm = new VirtualMachine();
		vm.setName("vm1111");
		vm.setHostId(10);
		dao.createVirtualMachine(vm);
	}
	
	@Test
	public void testSelect(){
		VirtualMachine vm = this.dao.getVirtualMachineById(1);
		System.out.println("host****"+vm.getHost());
	}
	
	//@Test
	public void testUpdate(){
		VirtualMachine vm = this.dao.getVirtualMachineByName("vm1");	
		vm.setName("vm2");
		this.dao.updateVirtualMachine(vm);
	}
	
	//@Test
	public void testSaveOrUpdate(){
		VirtualMachine vm = new VirtualMachine();
		vm.setName("vm1111");
		vm.setCpuCount((short)100);
		//this.dao.saveOrUpdate(vm);
		
		vm.setName("vm2222");
		vm.setCpuCount((short)100);
		this.dao.saveOrUpdate(vm);
	}
}
