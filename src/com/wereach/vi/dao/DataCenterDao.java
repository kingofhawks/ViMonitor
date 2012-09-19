package com.wereach.vi.dao;

import java.util.List;
import com.wereach.vi.model.DataCenter;

public interface DataCenterDao {
	public DataCenter getDataCenterById(int id);
	
	public DataCenter getDataCenterByName(String name);
	
	public void createDataCenter(DataCenter host);	
	
	public List<DataCenter> getAllDataCenters();
	
	public int updateDataCenter(DataCenter dc);
	
	public void saveOrUpdate(DataCenter dc);
	
	public void clearInventory();
}
