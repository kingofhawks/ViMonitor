package com.wereach.vi.dao;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.wereach.vi.model.HostSystem;


public class IbatisSessionUtil {
	private static SqlSessionFactory sqlMapper;
	
	static {
		String resource = "com/wereach/vi/dao/Configuration.xml"; 
		try{
			Reader reader = Resources.getResourceAsReader(resource); 
			sqlMapper = new SqlSessionFactoryBuilder().build(reader); 
			System.out.println("**********Init Ibatis session factory*****"+sqlMapper);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSession(){
		return sqlMapper.openSession(true);
	}
	
	public static void main(String[] args)throws Exception{
		SqlSession session = IbatisSessionUtil.getSession();
		try { 
			  HostSystem blog = (HostSystem) session.selectOne( 
			    "com.wereach.vi.model.HostSystemMapper.selectHost", 101); 
			  System.out.println("*********host****"+blog);
			} finally { 
			  session.close(); 
			} 
	}
}
