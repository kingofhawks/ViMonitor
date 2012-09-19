package com.wereach.vi.dao.ibatis;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.wereach.vi.dao.DataStoreDao;
import com.wereach.vi.dao.IbatisSessionUtil;
import com.wereach.vi.model.DataStore;


public class DataStoreDaoImpl implements DataStoreDao {

	public int createDataStore(DataStore store) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.insert("com.wereach.vi.model.DataStoreMapper.insertStore", store); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


	public void saveOrUpdate(DataStore store) {
		// TODO Auto-generated method stub
		DataStore ds = this.getDataStoreByName(store.getName());
		if (ds == null){
			this.createDataStore(store);
		} else {
			store.setId(ds.getId());
			this.updateDataStore(store);
		}
	}


	public int updateDataStore(DataStore store) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.update("com.wereach.vi.model.DataStoreMapper.updateStore", store); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


	public DataStore getDataStoreById(int id) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		DataStore ds = null;
		try { 
			  ds = (DataStore) session.selectOne( 
			    "com.wereach.vi.model.DataStoreMapper.selectStore",id); 			 
			} finally { 
			  session.close(); 			  
			} 
		return ds;
	}

	public DataStore getDataStoreByName(String name) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		DataStore ds = null;
		try { 
			  ds = (DataStore) session.selectOne( 
			    "com.wereach.vi.model.DataStoreMapper.selectStoreByName",name); 			  
			} finally { 
			  session.close(); 			  
			} 
		return ds;
	}


	public List<DataStore> getAllStores() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<DataStore> stores = null;
		try { 
			stores = session.selectList( 
			    "com.wereach.vi.model.DataStoreMapper.selectAll"); 			  
			} finally { 
			  session.close(); 			  
			} 
		return stores;
	}


	public List<DataStore> getDataStoreByHost(String host) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<DataStore> stores = null;
		try { 
			stores = session.selectList( 
			    "com.wereach.vi.model.DataStoreMapper.selectStoreByHost",host); 			  
			} finally { 
			  session.close(); 			  
			} 
		return stores;
	}

}
