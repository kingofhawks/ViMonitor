package com.wereach.vi.web;

import org.apache.log4j.Logger;

import com.vmware.apputils.AppUtil;
import com.vmware.samples.scsilun.SCSILunName;

public class Hello {
	private static Logger logger = Logger.getLogger(Hello.class);
	
	String[] args= {
			"--hostname",
			"127.1.1.1" ,
			"--url",
			"https://127.1.1.2/sdk",
			"--username",
			"administrator",
			"--password",
			"123",
			"--ignorecert"
		};
	
	public String start(String message) {

		SCSILunName obj = new SCSILunName();

		try {
			obj.cb = AppUtil.initialize("SCSILunName", SCSILunName
					.constructOptions(), args);
			logger.info("*************Connect for SCSILunName********");
			obj.cb.connect();
			obj.displayScsiLuns();
			obj.cb.disConnect();
			logger.info("*************Finish Connect for SCSILunName********");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("**********Fail to invoke SCSILunName**********" );
		}

		return "Displays the CanonicalName, Vendor, Model, Data, Namespace and NamespaceId of the host SCSI LUN name."
			 + " Successfully.";

	}
	

}
