package com.wereach.vi.dao;

import java.util.List;
import com.wereach.vi.model.VirtualMachine;

public interface VirtualMachineDao {
	public VirtualMachine getVirtualMachineById(int id);
	
	public VirtualMachine getVirtualMachineByName(String name);
	
	public void createVirtualMachine(VirtualMachine vm);
	
	public void updateVirtualMachine(VirtualMachine vm);
	
	public void saveOrUpdate(VirtualMachine vm);
	
	public List<VirtualMachine> getAllVirtualMachines();
}
