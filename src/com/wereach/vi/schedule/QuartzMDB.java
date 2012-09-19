package com.wereach.vi.schedule;


import javax.ejb.MessageDriven;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wereach.vi.discover.ViDiscoverInterface;
//import com.wereach.vi.discover.impl.ViDiscoverImpl;
import com.wereach.vi.discover.impl.ViDiscoverImpl;
import com.wereach.vi.discover.impl.ViDiscoverySdkImpl;

import javax.ejb.ActivationConfigProperty;

@MessageDriven(activationConfig =
{@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "0 0/1 * * * ?")})
@ResourceAdapter("quartz-ra.rar")

public class QuartzMDB implements Job
{
	private static Logger logger = Logger.getLogger(QuartzMDB.class);
	
	private static boolean running;

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
	{
		logger.info("Quartz job executed to discover!");
		//TODO
		if (running){
			logger.warn("***Discovery already running****");
		}else{
			running = true;
			ViDiscoverInterface discover = new ViDiscoverImpl();
			discover.startDiscovery();
			running = false;
		}
	}

}
