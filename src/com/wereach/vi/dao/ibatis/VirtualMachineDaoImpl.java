package com.wereach.vi.dao.ibatis;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.wereach.vi.dao.IbatisSessionUtil;
import com.wereach.vi.dao.VirtualMachineDao;
import com.wereach.vi.model.VirtualMachine;

public class VirtualMachineDaoImpl implements VirtualMachineDao {

	public void createVirtualMachine(VirtualMachine vm) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();		
		try { 
			  session.insert("com.wereach.vi.model.VirtualMachineMapper.insertVirtualMachine", vm); 			  
			} finally { 
			  session.close(); 			  
			}
	}

	public void updateVirtualMachine(VirtualMachine vm) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();		
		try { 
			  session.update("com.wereach.vi.model.VirtualMachineMapper.updateVirtualMachine", vm); 			  
			} finally { 
			  session.close(); 			  
			}
	}

	public void saveOrUpdate(VirtualMachine vm) {
		// TODO Auto-generated method stub
		VirtualMachine vmInDb = this.getVirtualMachineByName(vm.getName());
		if (vmInDb == null){
			this.createVirtualMachine(vm);
		} else {
			vm.setId(vmInDb.getId());
			this.updateVirtualMachine(vm);
		}
	}

	public List<VirtualMachine> getAllVirtualMachines() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<VirtualMachine> dcs = null;
		try { 
			dcs = session.selectList( 
			    "com.wereach.vi.model.VirtualMachineMapper.selectAll"); 
			  System.out.println("*********host****"+dcs.size());
			} finally { 
			  session.close(); 			  
			} 
		return dcs;
	}

	public VirtualMachine getVirtualMachineById(int id) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		VirtualMachine dc = null;
		try { 
			  dc = (VirtualMachine) session.selectOne( 
			    "com.wereach.vi.model.VirtualMachineMapper.selectVirtualMachine", id); 
			  System.out.println("*********vm****"+dc);
			} finally { 
			  session.close(); 			  
			} 
		return dc;
	}

	public VirtualMachine getVirtualMachineByName(String name) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		VirtualMachine dc = null;
		try { 
			  dc = (VirtualMachine) session.selectOne( 
			    "com.wereach.vi.model.VirtualMachineMapper.selectVirtualMachineByName", name); 
			  System.out.println("*********vm****"+dc);
			} finally { 
			  session.close(); 			  
			} 
		return dc;
	}

}
