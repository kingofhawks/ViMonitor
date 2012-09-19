package com.wereach.vi.dao.ibatis;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.wereach.vi.dao.FolderDao;
import com.wereach.vi.dao.IbatisSessionUtil;
import com.wereach.vi.model.Folder;


public class FolderDaoImpl implements FolderDao {

	public int createFolder(Folder store) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.insert("com.wereach.vi.model.FolderMapper.insertFolder", store); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


	public void saveOrUpdate(Folder store) {
		// TODO Auto-generated method stub
		Folder ds = this.getFolderByName(store.getName());
		if (ds == null){
			this.createFolder(store);
		} else {
			store.setId(ds.getId());
			this.updateFolder(store);
		}
	}


	public int updateFolder(Folder folder) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		int result = 0;
		try { 
			result = session.update("com.wereach.vi.model.FolderMapper.updateFolder", folder); 			  
			} finally { 
			  session.close(); 			  
			} 		
			return result;
	}


	public Folder getFolderById(int id) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		Folder ds = null;
		try { 
			  ds = (Folder) session.selectOne( 
			    "com.wereach.vi.model.FolderMapper.selectFolder",id); 			 
			} finally { 
			  session.close(); 			  
			} 
		return ds;
	}

	public Folder getFolderByName(String name) {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		Folder ds = null;
		try { 
			  ds = (Folder) session.selectOne( 
			    "com.wereach.vi.model.FolderMapper.selectFolderByName",name); 			  
			} finally { 
			  session.close(); 			  
			} 
		return ds;
	}


	public List<Folder> getAllFolders() {
		// TODO Auto-generated method stub
		SqlSession session = IbatisSessionUtil.getSession();
		List<Folder> folders = null;
		try { 
			folders = session.selectList( 
			    "com.wereach.vi.model.FolderMapper.selectAll"); 			  
			} finally { 
			  session.close(); 			  
			} 
		return folders;
	}

}
