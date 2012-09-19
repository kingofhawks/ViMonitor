package com.wereach.vi.dao;

import java.util.List;
import com.wereach.vi.model.DataStore;

public interface DataStoreDao {
	public DataStore getDataStoreById(int id);
	
	public DataStore getDataStoreByName(String name);
	
	public int createDataStore(DataStore store);
	
	public int updateDataStore(DataStore store);
	
	public void saveOrUpdate(DataStore store);
	
	public List<DataStore> getAllStores();
	
	public List<DataStore> getDataStoreByHost(String host);
}
