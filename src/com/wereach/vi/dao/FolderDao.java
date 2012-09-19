package com.wereach.vi.dao;

import java.util.List;
import com.wereach.vi.model.Folder;

public interface FolderDao {
	public Folder getFolderById(int id);
	
	public Folder getFolderByName(String name);
	
	public int createFolder(Folder store);
	
	public int updateFolder(Folder store);
	
	public void saveOrUpdate(Folder store);
	
	public List<Folder> getAllFolders();
}
