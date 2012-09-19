package com.wereach.vi.dao.ibatis;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.wereach.vi.dao.DataCenterDao;
import com.wereach.vi.dao.IbatisSessionUtil;
import com.wereach.vi.model.DataCenter;

public class DataCenterDaoImpl implements DataCenterDao {

	public void createDataCenter(DataCenter dc) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();		
		try { 
			  session.insert("com.wereach.vi.model.DataCenterMapper.insertDc", dc); 			  
			} finally { 
			  session.close(); 			  
			}
	}

	public List<DataCenter> getAllDataCenters() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<DataCenter> dcs = null;
		try { 
			dcs = session.selectList( 
			    "com.wereach.vi.model.DataCenterMapper.selectAll"); 			  
			} finally { 
			  session.close(); 			  
			} 
		return dcs;
	}

	public DataCenter getDataCenterById(int id) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		DataCenter dc = null;
		try { 
			  dc = (DataCenter) session.selectOne( 
			    "com.wereach.vi.model.DataCenterMapper.selectDc", id); 			  
			} finally { 
			  session.close(); 			  
			} 
		return dc;
	}

	public DataCenter getDataCenterByName(String name) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		DataCenter dc = null;
		try { 
			  dc = (DataCenter) session.selectOne( 
			    "com.wereach.vi.model.DataCenterMapper.selectDcByName", name); 			  
			} finally { 
			  session.close(); 			  
			} 
		return dc;
	}

	public void saveOrUpdate(DataCenter dc) {
		// TODO Auto-generated method stub
		DataCenter dcInDb = this.getDataCenterByName(dc.getName());
		if (dcInDb == null){
			this.createDataCenter(dc);
		} else {
			dc.setId(dcInDb.getId());
			this.updateDataCenter(dc);
		}
	}

	public int updateDataCenter(DataCenter dc) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.update("com.wereach.vi.model.DataCenterMapper.updateDc", dc); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}

	public void clearInventory() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();		
		try { 
			  session.delete("com.wereach.vi.model.DataCenterMapper.deleteVm"); 
			  session.delete("com.wereach.vi.model.DataCenterMapper.deleteDatastore");
			  session.delete("com.wereach.vi.model.DataCenterMapper.deleteHost");
			  session.delete("com.wereach.vi.model.DataCenterMapper.deleteDc");
			} finally { 
			  session.close(); 			  
			}
	}

}
