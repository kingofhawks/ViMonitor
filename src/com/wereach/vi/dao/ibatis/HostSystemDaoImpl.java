package com.wereach.vi.dao.ibatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.wereach.vi.dao.HostSystemDao;
import com.wereach.vi.dao.IbatisSessionUtil;
import com.wereach.vi.model.HostSystem;

public class HostSystemDaoImpl implements HostSystemDao {

	public int createHostSystem(HostSystem host) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.insert("com.wereach.vi.model.HostSystemMapper.insertHost", host); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


	public void saveOrUpdate(HostSystem host) {
		// TODO Auto-generated method stub
		HostSystem hs = this.getHostSystemByName(host.getHostName());
		if (hs == null){
			this.createHostSystem(host);
		} else {
			host.setId(hs.getId());			
			this.updateHostSystem(host);
		}
	}


	public int updateHostSystem(HostSystem host) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.update("com.wereach.vi.model.HostSystemMapper.updateHost", host); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


	public HostSystem getHostSystemById(int id) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		HostSystem host = null;
		try { 
			  host = (HostSystem) session.selectOne( 
			    "com.wereach.vi.model.HostSystemMapper.selectHost",id); 			 
			} finally { 
			  session.close(); 			  
			} 
		return host;
	}

	public HostSystem getHostSystemByName(String name) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		HostSystem host = null;
		try { 
			  host = (HostSystem) session.selectOne( 
			    "com.wereach.vi.model.HostSystemMapper.selectHostByName",name); 			 
			} finally { 
			  session.close(); 			  
			} 
		return host;
	}


	public List<HostSystem> getAllHosts() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<HostSystem> hosts = null;
		try { 
			hosts = session.selectList( 
			    "com.wereach.vi.model.HostSystemMapper.selectAll"); 			  
			} finally { 
			  session.close(); 			  
			} 
		return hosts;
	}


	public List<HostSystem> getHostsByDataCenter(String dataCenter) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<HostSystem> hosts = null;
		try { 
			hosts = session.selectList( 
			    "com.wereach.vi.model.HostSystemMapper.selectHostsByDataCenter",dataCenter); 			  
			} finally { 
			  session.close(); 			  
			} 
		return hosts;
	}

}
