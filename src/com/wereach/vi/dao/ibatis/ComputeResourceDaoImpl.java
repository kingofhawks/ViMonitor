package com.wereach.vi.dao.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import com.wereach.vi.dao.ComputeResourceDao;
import com.wereach.vi.dao.IbatisSessionUtil;
import com.wereach.vi.model.ClusterComputeResource;
import com.wereach.vi.model.ComputeResource;

public class ComputeResourceDaoImpl implements ComputeResourceDao {
	
	public void createComputeResource(ComputeResource ccr) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();		
		try { 
			  session.insert("com.wereach.vi.model.ComputeResourceMapper.insertResource", ccr); 			  
			} finally { 
			  session.close(); 			  
			}
	}

	public List<ComputeResource> getAllComputeResources() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<ComputeResource> dcs = null;
		try { 
			dcs = session.selectList( 
			    "com.wereach.vi.model.ComputeResourceMapper.selectAllResources"); 			  
			} finally { 
			  session.close(); 			  
			} 
		return dcs;
	}

	public List<ClusterComputeResource> getAllClusterComputeResources() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<ComputeResource> dcs = null;		
		try { 
			dcs = session.selectList( 
			    "com.wereach.vi.model.ComputeResourceMapper.selectAllClusterResources"); 			  
			} finally { 
			  session.close(); 			  
			} 
			
		List<ClusterComputeResource> clusters = new ArrayList<ClusterComputeResource>();
		if (dcs!= null && dcs.size()!=0 ){
			for (ComputeResource res : dcs){
				ClusterComputeResource cluster = new ClusterComputeResource();
				cluster.setId(res.getId());
				cluster.setName(res.getName());
				cluster.setHosts(res.getHosts());
				clusters.add(cluster);
			}
		}
		
		return clusters;
	}

	public ComputeResource getComputeResourceById(int id) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		ComputeResource dc = null;
		try { 
			  dc = (ComputeResource) session.selectOne( 
			    "com.wereach.vi.model.ComputeResourceMapper.selectResource", id); 			  
			} finally { 
			  session.close(); 			  
			} 
		return dc;
	}

	public ComputeResource getComputeResourceByName(String name) {
		// TODO Auto-generated method stub**
		System.out.println("**********GET***"+name);
		SqlSession session = IbatisSessionUtil.getSession();
		ComputeResource dc = null;
		try { 
			  dc = (ComputeResource) session.selectOne( 
			    "com.wereach.vi.model.ComputeResourceMapper.selectResourceByName", name); 			  
			} finally { 
			  session.close(); 			  
			} 
		return dc;
	}

	public void saveOrUpdate(ComputeResource ccr) {
		// TODO Auto-generated method stub
		ComputeResource dcInDb = this.getComputeResourceByName(ccr.getName());
		System.out.println("**********db****"+dcInDb);
		if (dcInDb == null){
			this.createComputeResource(ccr);
		} else {
			ccr.setId(dcInDb.getId());
			this.updateComputeResource(ccr);
		}
	}

	public int updateComputeResource(ComputeResource ccr) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.update("com.wereach.vi.model.ComputeResourceMapper.updateResource", ccr); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


}
