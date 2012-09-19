package com.wereach.vi.discover.impl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vmware.vim25.DatastoreInfo;
import com.vmware.vim25.DynamicProperty;
import com.vmware.vim25.HostConfigSummary;
import com.vmware.vim25.HostHardwareSummary;
import com.vmware.vim25.HostHostBusAdapter;
import com.vmware.vim25.HostListSummary;
import com.vmware.vim25.HostMultipathInfo;
import com.vmware.vim25.HostScsiTopology;
import com.vmware.vim25.HostScsiTopologyInterface;
import com.vmware.vim25.HostScsiTopologyLun;
import com.vmware.vim25.HostScsiTopologyTarget;
import com.vmware.vim25.HostStorageDeviceInfo;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.ObjectContent;
import com.vmware.vim25.ObjectSpec;
import com.vmware.vim25.PerfCounterInfo;
import com.vmware.vim25.PerfEntityMetric;
import com.vmware.vim25.PerfEntityMetricBase;
import com.vmware.vim25.PerfEntityMetricCSV;
import com.vmware.vim25.PerfInterval;
import com.vmware.vim25.PerfMetricId;
import com.vmware.vim25.PerfMetricIntSeries;
import com.vmware.vim25.PerfMetricSeries;
import com.vmware.vim25.PerfMetricSeriesCSV;
import com.vmware.vim25.PerfProviderSummary;
import com.vmware.vim25.PerfQuerySpec;
import com.vmware.vim25.PerfSampleInfo;
import com.vmware.vim25.PerfStatsType;
import com.vmware.vim25.PerfSummaryType;
import com.vmware.vim25.PropertyFilterSpec;
import com.vmware.vim25.PropertySpec;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.ScsiLun;
import com.vmware.vim25.SelectionSpec;
import com.vmware.vim25.ServiceContent;
import com.vmware.vim25.VimPortType;
//import com.vmware.vim25.VimServiceLocator;

import com.vmware.vim25.TraversalSpec;
import com.vmware.vim25.VirtualMachineConfigSummary;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.PropertyCollector;
import com.vmware.vim25.mo.ServiceInstance;
//import com.vmware.vim25.mo.ManagedEntity;
/*import com.vmware.vim25.mo.ClusterComputeResource;
import com.vmware.vim25.mo.ComputeResource;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.PerformanceManager;
import com.vmware.vim25.mo.ResourcePool;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;*/
import com.wereach.vi.dao.ComputeResourceDao;
import com.wereach.vi.dao.DataCenterDao;
import com.wereach.vi.dao.DataStoreDao;
import com.wereach.vi.dao.HostSystemDao;
import com.wereach.vi.dao.VirtualMachineDao;
import com.wereach.vi.dao.ibatis.ComputeResourceDaoImpl;
import com.wereach.vi.dao.ibatis.DataCenterDaoImpl;
import com.wereach.vi.dao.ibatis.DataStoreDaoImpl;
import com.wereach.vi.dao.ibatis.HostSystemDaoImpl;
import com.wereach.vi.dao.ibatis.VirtualMachineDaoImpl;
import com.wereach.vi.discover.ViDiscoverInterface;
import com.wereach.vi.model.DataCenter;
import com.wereach.vi.model.DataStore;
//import com.wereach.vi.model.ManagedEntity;


public class ViDiscoverImpl implements ViDiscoverInterface {
	private static Logger logger = Logger.getLogger(ViDiscoverImpl.class);
	
	private HostSystemDao hostDao = new HostSystemDaoImpl();
	
	private VirtualMachineDao vmDao = new VirtualMachineDaoImpl();
	
	private DataStoreDao storeDao = new DataStoreDaoImpl();
	
	private DataCenterDao dcDao = new DataCenterDaoImpl();
	
	private ComputeResourceDao crDao = new ComputeResourceDaoImpl();
	
	private Map _pci = new HashMap();
	
	private String serverIp = "https://127.1.1.2/sdk";
	
	private String username = "administrator";
	
	private String password = "123";
	
	private String[] args = new String[]{serverIp,username, password};
	
	private boolean ingoreCert = true;
		
	private boolean logoutFlag = false;
	
	//private VimServiceLocator vsl;
	//private VimPortType vimService; 
	private ServiceInstance si;
	private ServiceContent sc;
	private boolean persist = true;
	
	public ViDiscoverImpl(){
		//this.si = this.createServiceInstance(serverIp, username, password, ingoreCert);
		this.login();
	}
	
	
	private void login(){
		try{
			//ignore the SSL certificate
		    System.setProperty(
		      "org.apache.axis.components.net.SecureSocketFactory",
		      "org.apache.axis.components.net.SunFakeTrustSocketFactory"
		      );
				 
		    //create the interface on which services are defined
		   /* vsl = new VimServiceLocator();
		    vsl.setMaintainSession(true);
		    vimService = vsl.getVimPort(new URL(args[0]+"/vimService"));*/
		    si = new ServiceInstance(new URL(serverIp), username, password, ingoreCert);
		    //vimService = new VimPortType(args[0]+"/vimService", true);
		  //create a ManagedObjectReference to the ServiceInstance
		    ManagedObjectReference siMOR = new ManagedObjectReference();
		    siMOR.set_value("ServiceInstance");
		    siMOR.setType("ServiceInstance");
		    
		  //retrieve ServiceContent data object from ServiceInstance
		    sc = si.getServiceContent();
		    //sc = vimService.retrieveServiceContent(siMOR);
		    //log in with SessionManager
		    /*ManagedObjectReference sessionMOR = sc.getSessionManager();
		    vimService.login(sessionMOR, args[1], args[2], null);*/
		    logger.info("****Login ESX Server success****");
		}catch(Exception e){
			logger.error("***Fail to login***"+e.getMessage(),e);
		}
	}

	 // Specifications to find all the Datacenters and
	   // retrieve their name, vmFolder and hostFolder values.
	   //
	   private ObjectContent[] getDatacenters() 
	      throws InvalidProperty, RemoteException {

	      // The PropertySpec object specifies what properties
	      // to retrieve from what type of Managed Object
	      PropertySpec pSpec = new PropertySpec();
	      pSpec.setType("Datacenter");
	      pSpec.setPathSet(new String[] { 
	              "name", "vmFolder", "hostFolder" });

	      // The following TraversalSpec and SelectionSpec
	      // objects create the following relationship:
	      //
	      // a. Folder -> childEntity
	      //   b. recurse to a.
	      //
	      // This specifies that starting with a Folder
	      // managed object, traverse through its childEntity
	      // property. For each element in the childEntity
	      // property, process by going back to the 'parent'
	      // TraversalSpec.
	      // SelectionSpec to cause Folder recursion
	      SelectionSpec recurseFolders = new SelectionSpec();
	      // The name of a SelectionSpec must refer to a
	      // TraversalSpec with the same name value.
	      recurseFolders.setName("folder2childEntity");
	      // Traverse from a Folder through the 'childEntity' property
	      TraversalSpec folder2childEntity = new TraversalSpec();
	      // Select the Folder type managed object
	      folder2childEntity.setType("Folder");
	      // Traverse through the childEntity property of the Folder
	      folder2childEntity.setPath("childEntity");
	      // Name this TraversalSpec so the SelectionSpec above
	      // can refer to it
	      folder2childEntity.setName(recurseFolders.getName());
	      // Add the SelectionSpec above to this traversal so that 
	      // we will recurse through the tree via the childEntity 
	      // property
	      folder2childEntity.setSelectSet(new SelectionSpec[] { 
	          recurseFolders });

	      // The ObjectSpec object specifies the starting object and
	      // any TraversalSpecs used to specify other objects
	      // for consideration
	      ObjectSpec oSpec = new ObjectSpec();
	      oSpec.setObj(this.sc.getRootFolder());
	      // We set skip to true because we are not interested
	      // in retrieving properties from the root Folder
	      oSpec.setSkip(Boolean.TRUE);
	      // Specify the TraversalSpec. This is what causes
	      // other objects besides the starting object to
	      // be considered part of the collection process
	      oSpec.setSelectSet(new SelectionSpec[] { folder2childEntity });

	      // The PropertyFilterSpec object is used to hold the 
	      // ObjectSpec and PropertySpec objects for the call
	      PropertyFilterSpec pfSpec = new PropertyFilterSpec();
	      pfSpec.setPropSet(new PropertySpec[] { pSpec });
	      pfSpec.setObjectSet(new ObjectSpec[] { oSpec });
	      // retrieveProperties() returns the properties
	      // selected from the PropertyFilterSpec
	      return this.si.getPropertyCollector().retrieveProperties(new PropertyFilterSpec[] { pfSpec });
	   }
	 //
    // Specifications to find all items in inventory and
    // retrieve their name and parent values.
    //
    private ObjectContent[] getInventory() throws InvalidProperty,
    RemoteException {
        
        // PropertySpec specifies what properties to
        // retrieve from what type of Managed Object
        PropertySpec pSpec = new PropertySpec();
        // We use ManagedEntity for the type because all
        // items in inventory are derived from the ManagedEntity
        // managed object.
        pSpec.setType("ManagedEntity");
        pSpec.setPathSet(new String[] { "name", "parent" });
        
        // The following TraversalSpec and SelectionSpec
        // objects create the following relationship:
        //
        // a. Folder -> childEntity
        //   b. recurse to a.
        //   c. Datacenter -> vmFolder
        //     d. Folder -> childEntity
        //       e. recurse to d.
        //   f. Datacenter -> hostFolder
        //     g. Folder -> childEntity
        //       h. recurse to g.
        //     i. ComputeResource -> host
        //     j. ComputeResource -> resourcePool
        //       k. ResourcePool -> resourcePool
        //         l. recurse to k.
        //
        // Notice how this mirrors how inventory is laid out
        // 
        // k. Traverse from a ResourcePool 
        // through the 'resourcePool' property
     // folder traversal reference
        SelectionSpec sSpecR = new SelectionSpec();
        sSpecR.setName("resourcePoolRecurse");
        
        TraversalSpec resourcePool2resourcePool = 
            new TraversalSpec();
        resourcePool2resourcePool.setType("ResourcePool");
        resourcePool2resourcePool.setPath("resourcePool");
        resourcePool2resourcePool.setName("resourcePoolRecurse");
        resourcePool2resourcePool.setSelectSet(
                // Add l. traversal
                new SelectionSpec[] {sSpecR});
        
        // j. Traverse from a ComputeResource 
        // through the 'resourcePool' property
        TraversalSpec computeResource2resourcePool 
            = new TraversalSpec();
        computeResource2resourcePool.setType("ComputeResource");
        computeResource2resourcePool.setPath("resourcePool");
        computeResource2resourcePool.setSelectSet(
                // Add k. traversal
                new SelectionSpec[]{ resourcePool2resourcePool });
        
        // i. Traverse from a ComputeResource 
        // through the 'host' property
        TraversalSpec computeResource2host = new TraversalSpec();
        computeResource2host.setType("ComputeResource");
        computeResource2host.setPath("host");
        
        // g. Traverse from a Folder through the 'childEntity' property
        // This traversal is specific to the 'hostFolder' 
        // Folder of the Datacenter
        // folder traversal reference
        SelectionSpec sSpecF = new SelectionSpec();
        sSpecF.setName("hostFolderRecurse");
        TraversalSpec hostFolder2childEntity = new TraversalSpec();
        hostFolder2childEntity.setType("Folder");
        hostFolder2childEntity.setPath("childEntity");
        hostFolder2childEntity.setName("hostFolderRecurse");
        hostFolder2childEntity.setSelectSet(new SelectionSpec[] {
                // Add h. traversal
        		sSpecF,
                // Add i. and j. traversals 
                computeResource2resourcePool, computeResource2host, });
        
        // f. Traverse from a Datacenter 
        // through the 'hostFolder' property
        TraversalSpec dc2hostFolder = new TraversalSpec();
        dc2hostFolder.setType("Datacenter");
        dc2hostFolder.setPath("hostFolder");
        dc2hostFolder.setSelectSet(
                // Add g. traversal
                new SelectionSpec[] { hostFolder2childEntity, });
        
        // d. Traverse from a Folder through 
        // the 'childEntity' property
        // This traversal is specific to the 
        // 'vmFolder' Folder of the Datacenter
        SelectionSpec sSpecV = new SelectionSpec();
        sSpecV.setName("vmFolderRecurse");
        
        TraversalSpec vmFolder2childEntity = new TraversalSpec();
        vmFolder2childEntity.setType("Folder");
        vmFolder2childEntity.setPath("childEntity");
        vmFolder2childEntity.setName("vmFolderRecurse");
        vmFolder2childEntity.setSelectSet(
                // e. recurse to d. 
                new SelectionSpec[] { sSpecV});
        
        // c. Traverse from a Datacenter 
        // through the 'vmFolder' property
        TraversalSpec dc2vmFolder = new TraversalSpec();
        dc2vmFolder.setType("Datacenter");
        dc2vmFolder.setPath("vmFolder");
        dc2vmFolder.setSelectSet(
                new SelectionSpec[] { vmFolder2childEntity });
        
        // a. Traverse from a Folder through the 'childEntity' property
        // This traversal is specific to the 'rootFolder' Folder
        // of the ServiceInstance
        SelectionSpec sSpecRoot = new SelectionSpec();
        sSpecRoot.setName("rootFolderRecurse");
        
        TraversalSpec rootFolder2childEntity = new TraversalSpec();
        rootFolder2childEntity.setType("Folder");
        rootFolder2childEntity.setPath("childEntity");
        rootFolder2childEntity.setName("rootFolderRecurse");
        rootFolder2childEntity.setSelectSet(new SelectionSpec[] {
                // b. recurse to a.
        		sSpecRoot,
                // Add c. and d. traversals
                dc2vmFolder, dc2hostFolder });
        
        // ObjectSpec specifies the starting object and
        // any TraversalSpecs used to specify other objects
        // for consideration
        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(sc.getRootFolder());
        oSpec.setSelectSet(
                // Add the a. traversal
                new SelectionSpec[] { rootFolder2childEntity });
        
        // PropertyFilterSpec is used to hold the ObjectSpec and
        // PropertySpec for the call
        PropertyFilterSpec pfSpec = new PropertyFilterSpec();
        pfSpec.setPropSet(new PropertySpec[] { pSpec });
        pfSpec.setObjectSet(new ObjectSpec[] { oSpec });
        
        // retrieveProperties() returns the properties
        // selected from the PropertyFilterSpec
        return this.si.getPropertyCollector().retrieveProperties(new PropertyFilterSpec[] { pfSpec });
        /*return vimService.retrieveProperties(sc
                .getPropertyCollector(),
                new PropertyFilterSpec[] { pfSpec });*/
    }
    
    static class MeNode {
        private ManagedObjectReference parent;
        private ManagedObjectReference node;
        private String name;
        private ArrayList children = new ArrayList();
        
        public MeNode(ManagedObjectReference parent,
                ManagedObjectReference node, String name) {
            this.setParent(parent);
            this.setNode(node);
            this.setName(name);
        }

        public void setParent(ManagedObjectReference parent) {
            this.parent = parent;
        }

        public ManagedObjectReference getParent() {
            return parent;
        }

        public void setNode(ManagedObjectReference node) {
            this.node = node;
        }

        public ManagedObjectReference getNode() {
            return node;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setChildren(ArrayList children) {
            this.children = children;
        }

        public ArrayList getChildren() {
            return children;
        }
    }
    
    //
    // Recursive method to print an inventory tree node
    //
    static void printNode(MeNode node, int indent) {
        // Make it pretty
        for (int i = 0; i < indent; ++i) {
            logger.info(' ');
        }
        logger.info(node.getName() + 
                " (" + node.getNode().getType() + ")");
        if (!node.getChildren().isEmpty()) {
            for (int c = 0; c < node.getChildren().size(); ++c) {
                printNode((MeNode) 
                        node.getChildren().get(c), indent + 2);
            }
        }
    }
    
    //
    // Print the inventory tree retrieved from
    // the PropertyCollector
    private void printInventoryTree(ObjectContent[] ocs) {
        // Hashtable MoRef.Value -> MeNode
        Hashtable nodes = new Hashtable();
        // The root folder node
        MeNode root = null;
        
        for (int oci = 0; oci < ocs.length; ++oci) {
            ObjectContent oc = ocs[oci];
            ManagedObjectReference mor = oc.getObj();
            DynamicProperty[] dps = oc.getPropSet();
            if (dps != null) {
                ManagedObjectReference parent = null;
                String name = null;
                for (int dpi = 0; dpi < dps.length; ++dpi) {
                    DynamicProperty dp = dps[dpi];
                    if (dp != null) {
                        if ("name".equals(dp.getName())) {
                            name = (String) dp.getVal();
                        }
                        else if ("parent".equals(dp.getName())) {
                            parent = (ManagedObjectReference) dp
                            .getVal();
                        } else {
                        	logger.info("****Property Value : " + dp.getVal());
                        }
                    }
                }
                // Create a MeNode to hold the data
                MeNode node = new MeNode(parent, mor, name);
                // The root folder has no parent
                if (parent == null) {
                    root = node;
                }
                // Add the node
                nodes.put(node.getNode().get_value(), node);
                logger.info("****Inventory name****"+name+"***prop size****"+dps.length);
            }
        }
        
        // Build the nodes into a tree
        for (Iterator it = nodes.values().iterator(); it.hasNext();) {
            MeNode meNode = (MeNode) it.next();
            if (meNode.getParent() != null) {
                MeNode parent = (MeNode) nodes.get(
                        meNode.getParent().get_value());
                parent.getChildren().add(meNode);
            }
        }
        logger.info("Inventory Tree");
        printNode(root, 0);
    }
    
    // Print out the ObjectContent[] 
    // returned from retrieveProperties()
    private void printObjectContent(ObjectContent[] ocs, 
       String title) {
       if (ocs != null) {
    	   // Print out the title to label the output
           logger.info(title+"****Size****"+ocs.length);           
             for (int i = 0; i < ocs.length; ++i) {
                ObjectContent oc = ocs[i];
                // Print out the managed object type
                logger.info(oc.getObj().getType());
                logger.info("  Property Name:Value");
                DynamicProperty[] dps = oc.getPropSet();
                   if (dps != null) {
                      for (int j = 0; j < dps.length; ++j) {
                         DynamicProperty dp = dps[j];
                         // Print out the property name and value
                         logger.info("  " + dp.getName() + ": "
                                 + dp.getVal());
                      }
                   }
             }
       }
    }
 // Retrieve properties from a single MoRef
    private Object[] getProperties(ManagedObjectReference moRef, 
          String[] properties)
    throws RuntimeFault, RemoteException {
    PropertySpec pSpec = new PropertySpec();
    pSpec.setType(moRef.getType());
    pSpec.setPathSet(properties);

    ObjectSpec oSpec = new ObjectSpec();
    // Set the starting object
    oSpec.setObj(moRef);
    PropertyFilterSpec pfSpec = new PropertyFilterSpec();
    pfSpec.setPropSet(new PropertySpec[] {pSpec});
    pfSpec.setObjectSet(new ObjectSpec[] {oSpec});
    ObjectContent[] ocs = this.si.getPropertyCollector().retrieveProperties(
    		new PropertyFilterSpec[] { pfSpec });

       Object[] ret = new Object[properties.length];
       
    if(ocs != null) {
       for(int i=0; i<ocs.length; ++i) {
          ObjectContent oc = ocs[i];
          DynamicProperty[] dps = oc.getPropSet();
          if(dps != null) {
             for(int j=0; j<dps.length; ++j) {
                DynamicProperty dp = dps[j];
                   for(int p=0; p<ret.length; ++p) {
                         if(properties[p].equals(dp.getName())) {
                            ret[p] = dp.getVal();
                         }
                   }
             }
          }
       }
    }
    return ret;
 }
    //
    // Specifications to find all the VMs in a Datacenter and
    // retrieve their name and runtime.powerState values.
    //
    private ObjectContent[] getVMs(ManagedObjectReference dcMoRef)
          throws InvalidProperty, RemoteException {
       // The PropertySpec object specifies what properties 
       // retrieve from what type of Managed Object
       PropertySpec pSpec = new PropertySpec();
       pSpec.setType("VirtualMachine");
       pSpec.setPathSet(new String[] { "name","parent",
               "summary.config.memorySizeMB", "summary.config.numCpu"});
       SelectionSpec recurseFolders = new SelectionSpec();
       recurseFolders.setName("folder2childEntity");
       TraversalSpec folder2childEntity = new TraversalSpec();
       folder2childEntity.setType("Folder");
       folder2childEntity.setPath("childEntity");
       folder2childEntity.setName(recurseFolders.getName());
       folder2childEntity.setSelectSet(
          new SelectionSpec[] { recurseFolders });
       // Traverse from a Datacenter through the 'vmFolder' property
       TraversalSpec dc2vmFolder = new TraversalSpec();
       dc2vmFolder.setType("Datacenter");
       dc2vmFolder.setPath("vmFolder");
       dc2vmFolder.setSelectSet(
          new SelectionSpec[] { folder2childEntity });
       ObjectSpec oSpec = new ObjectSpec();
       oSpec.setObj(dcMoRef);
       oSpec.setSkip(Boolean.TRUE);
       oSpec.setSelectSet(new SelectionSpec[] { dc2vmFolder });
       PropertyFilterSpec pfSpec = new PropertyFilterSpec();
       pfSpec.setPropSet(new PropertySpec[] { pSpec });
       pfSpec.setObjectSet(new ObjectSpec[] { oSpec });
       return this.si.getPropertyCollector().retrieveProperties(new PropertyFilterSpec[] { pfSpec });
    }
    private ObjectContent[] getHosts(ManagedObjectReference dcMoRef)
           throws InvalidProperty, RemoteException {
       // PropertySpec specifies what properties to
       // retrieve from what type of Managed Object
       PropertySpec pSpec = new PropertySpec();
       pSpec.setType("HostSystem");
       pSpec.setPathSet(new String[] 
          { "name","parent",
               "summary.hardware.memorySize", "summary.hardware.numCpuCores",
               "summary.hardware.cpuMhz", "summary.config.product.vendor",
               "datastore","config.storageDevice"});
       SelectionSpec recurseFolders = new SelectionSpec();
       recurseFolders.setName("folder2childEntity");
       TraversalSpec computeResource2host = new TraversalSpec();
       computeResource2host.setType("ComputeResource");
       computeResource2host.setPath("host");
       TraversalSpec folder2childEntity = new TraversalSpec();
       folder2childEntity.setType("Folder");
       folder2childEntity.setPath("childEntity");
       folder2childEntity.setName(recurseFolders.getName());
       // Add BOTH of the specifications to this specification
       folder2childEntity.setSelectSet(new SelectionSpec[] 
          { recurseFolders, computeResource2host });

       // Traverse from a Datacenter through 
       // the 'hostFolder' property
       TraversalSpec dc2hostFolder = new TraversalSpec();
       dc2hostFolder.setType("Datacenter");
       dc2hostFolder.setPath("hostFolder");
       dc2hostFolder.setSelectSet(new SelectionSpec[] 
          { folder2childEntity });
       ObjectSpec oSpec = new ObjectSpec();
       oSpec.setObj(dcMoRef);
       oSpec.setSkip(Boolean.TRUE);
       oSpec.setSelectSet(new SelectionSpec[] { dc2hostFolder });
       PropertyFilterSpec pfSpec = new PropertyFilterSpec();
       pfSpec.setPropSet(new PropertySpec[] { pSpec });
       pfSpec.setObjectSet(new ObjectSpec[] { oSpec });
       return this.si.getPropertyCollector().retrieveProperties(new PropertyFilterSpec[] { pfSpec });
    }
    
    private ObjectContent[] getDatastores(ManagedObjectReference dcMoRef)
	    throws InvalidProperty, RemoteException {
		 // The PropertySpec object specifies what properties 
		 // retrieve from what type of Managed Object
		 PropertySpec pSpec = new PropertySpec();
		 pSpec.setType("Datastore");
		 pSpec.setPathSet(new String[] { "name","parent",
		         "info.freeSpace", "info.maxFileSize","info.url"});		 
		 
		 // Traverse from a Datacenter through the 'datastore' property
		 TraversalSpec dc2Datastore = new TraversalSpec();
		 dc2Datastore.setType("Datacenter");
		 dc2Datastore.setPath("datastore");
		 
		 ObjectSpec oSpec = new ObjectSpec();
		 oSpec.setObj(dcMoRef);
		 oSpec.setSkip(Boolean.TRUE);
		 oSpec.setSelectSet(new SelectionSpec[] { dc2Datastore });
		 PropertyFilterSpec pfSpec = new PropertyFilterSpec();
		 pfSpec.setPropSet(new PropertySpec[] { pSpec });
		 pfSpec.setObjectSet(new ObjectSpec[] { oSpec });
		 return this.si.getPropertyCollector().retrieveProperties(new PropertyFilterSpec[] { pfSpec });
	}
    
	public void startDiscovery(){
		// TODO Auto-generated method stub
		try{
			logger.info("*********Start discover********");
			if (this.sc == null){
				logger.error("***Can not discover without ServiceContent****");
				//return ;
			}
			long begin = System.currentTimeMillis();
			//Clear database
			this.dcDao.clearInventory();
			
			ObjectContent[] ocs = this.getDatacenters();
			printObjectContent(ocs, "All Datacenters");
			
			if (ocs!= null && ocs.length!= 0){
				for (ObjectContent oc: ocs){
					ManagedObjectReference dcMoRef = oc.getObj();
					String dcName = (String)this.getProperties(dcMoRef, new String[]{"name"})[0];
					this.discoverDatacenter(dcMoRef, dcName);					
				}				
			}
            
			/*Folder rootFolder = si.getRootFolder();
			
			logger.info("============ Data Centers ============");
			ManagedEntity[] dcs = new InventoryNavigator(rootFolder).searchManagedEntities(
					new String[][] { {"Datacenter", "name" }, }, true);
			if (dcs!= null && dcs.length!= 0){
				logger.info("============ Data Centers size============"+dcs.length);
				for(int i=0; i<dcs.length; i++)
				{
					logger.debug("Datacenter["+i+"]=" + dcs[i].getName());
					if (dcs[i] instanceof Datacenter){
						Datacenter dc = (Datacenter)dcs[i];
						this.startDiscovery(dc.getName());
					}
				}	
			}	*/		
			long end = System.currentTimeMillis();
			logger.info("***Time elapsed for discovery****"+(end-begin));			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("****Fail to discovery***"+e.getMessage(),e);
		}finally{
			this.logout();
		}
	}
	
	private void logout(){
		try{
			this.si.getServerConnection().logout();
			//vimService.logout(sc.getSessionManager());
			//vimService = null;
			this.si = null;
	        sc = null;		
			this.logoutFlag = true;
		}catch(Exception e){
			logger.error("***Fail to logout***"+e.getMessage(),e);
		}
	}
	
	private void discoverDatacenter(ManagedObjectReference dcMoRef,String dcName) throws RemoteException{
		// Find all the VMs in the Datacenter
		ObjectContent[] vmOcs = getVMs(dcMoRef);
        printObjectContent(vmOcs,"All VMs in the Datacenter: " + dcName);
        //Find all the Hosts in the Datacenter
        ObjectContent[] hostOcs = getHosts(dcMoRef);
        printObjectContent(hostOcs,"All Hosts in the Datacenter: " + dcName);
      //Find all the Datastores in the Datacenter
        ObjectContent[] datastoreOcs = this.getDatastores(dcMoRef);
        printObjectContent(datastoreOcs,"All Datastores in the Datacenter: " + dcName);
        
        if (this.persist){
        	long beginSave = System.currentTimeMillis();
        	int dcId = this.saveDataCenter(dcName);
        	this.saveVm(vmOcs);
        	this.saveHostSystem(hostOcs, dcId);
        	this.saveDatastores(datastoreOcs, dcId);
        	long endSave = System.currentTimeMillis();
        	logger.info("***Time to save datacenter to database***"+(endSave-beginSave));
        }
	
	}

	public void startDiscovery(String dataCenter) {
		// TODO Auto-generated method stub
		try{
			logger.info("*********Start discover datacenter****"+dataCenter);
			long begin = System.currentTimeMillis();
			
			ObjectContent[] ocs = this.getDatacenters();
			printObjectContent(ocs, "All Datacenters");
			
			if (ocs!= null && ocs.length!= 0){
				for (ObjectContent oc: ocs){
					ManagedObjectReference dcMoRef = oc.getObj();
					String dcName = (String)this.getProperties(dcMoRef, new String[]{"name"})[0];
					if (dataCenter.equalsIgnoreCase(dcName)){
						this.discoverDatacenter(dcMoRef, dcName);
					}					
				}				
			}
			
			/*if (this.logoutFlag){
				this.si = this.createServiceInstance(serverIp, username, password, ingoreCert);
			}
			
			Folder rootFolder = si.getRootFolder();
			
			logger.info("============ Data Centers ============");
			Datacenter dc = (Datacenter)new InventoryNavigator(rootFolder).searchManagedEntity("Datacenter",dataCenter);
			if (dc == null){
				logger.error("****Fail to discover Datacenter****"+dataCenter);
				return ;				
			}
			int dcId = this.saveDataCenter(dc);
			
			Folder vmFolder = dc.getVmFolder();
			logger.info("*******vmFolder****"+vmFolder.getName());
			this.travelVmFolder(vmFolder);
			
			Folder hostFolder = dc.getHostFolder();
			logger.info("*******hostFolder****"+hostFolder.getName());
			this.travelHostFolder(hostFolder, dcId);
			
			Datastore[] stores = dc.getDatastores();
			if (stores!= null && stores.length!= 0 ){
				logger.info("\n============ DataStore size============"+stores.length);			
				long begin1= System.currentTimeMillis();
				for (Datastore store: stores){	
					this.saveDataStore(store, dcId, null);
				}
				long end1= System.currentTimeMillis();
				logger.info("****Time elapsed to discover Datastore "+(end1-begin1));
			}			
			
			//si.getServerConnection().logout();
*/			long end = System.currentTimeMillis();
			logger.info("****Time elapsed to discover data center "+dataCenter+"***"+(end-begin));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("***Fail to discover data center***"+e.getMessage(),e);
		}
	}
	
	static void printHBAs(HostHostBusAdapter[] hbas)
	  {
	    for(int i=0; hbas!=null && i<hbas.length; i++)
	    {
	      logger.info("Device:" + hbas[i].getDevice());
	      logger.info("Bus:" + hbas[i].getBus());
	      logger.info("Driver:" + hbas[i].getDriver());
	      logger.info("Key:" + hbas[i].getKey());
	      logger.info("Model:" + hbas[i].getModel());
	      logger.info("PCI:" + hbas[i].getPci());
	      logger.info("Status:" + hbas[i].getStatus());
	    }
	  }
	  
	  static void printScsiTopology(HostScsiTopology hst)
	  {
	    HostScsiTopologyInterface[] hstis = hst.getAdapter();
	    
	    for(int i=0; hstis!=null && i<hstis.length; i++)
	    {
	      logger.info("Adapter:" + hstis[i].getAdapter());
	      logger.info("Key:" + hstis[i].getKey());
	      HostScsiTopologyTarget[] hstts = hstis[i].getTarget();
	      
	      for(int j=0; hstts!=null && j<hstts.length; j++)
	      {
	        logger.info("Key:" + hstts[j].getKey());
	        logger.info("Target:" + hstts[j].getTarget());
	        logger.info("Transport:" 
	            + hstts[j].getTransport().getClass().getName());
	        HostScsiTopologyLun[] luns = hstts[j].getLun();
	        for(int k=0; luns!=null && k<luns.length; k++)
	        {
	          logger.debug("Key:" + luns[k].getKey());
	          logger.debug("LUN:" + luns[k].getLun());
	          logger.debug("ScsiLun:" + luns[k].getScsiLun());
	        }
	      }
	    }
	  }
	  
	  static void printScsiLuns(ScsiLun[] sls)
	  {
	    for(int i=0; sls!=null && i<sls.length; i++)
	    {
	      logger.info("UUID:" + sls[i].getUuid());
	      logger.info("CanonicalName:" 
	          + sls[i].getCanonicalName());
	      logger.info("LunType:" + sls[i].getLunType());
	      String[] states = sls[i].getOperationalState();
	      for(int j=0; states!=null && j<states.length; j++)
	      {
	    	  logger.debug(states[j] + " ");
	      }
	      logger.info("\nSCSI Level:" 
	          + sls[i].getScsiLevel());
	      logger.info("Vendor:" + sls[i].getVendor());
	    }
	  }
	  
	  static void printStorageDeviceInfo(HostStorageDeviceInfo hsdi)
	  {
	    logger.info("\nHost bus adapters");
	    printHBAs(hsdi.getHostBusAdapter());
	    
	    logger.info("\nSCSI LUNs");
	    printScsiLuns(hsdi.getScsiLun());
	    
	    HostScsiTopology hst = hsdi.getScsiTopology();
	    printScsiTopology(hst);
	    
	    logger.info("\nSoftware iSCSI enabled:" 
	        + hsdi.isSoftwareInternetScsiEnabled());
	  }
	  
	private int saveDataCenter(String name){
		DataCenter center = new DataCenter();
		center.setName(name);
		this.dcDao.saveOrUpdate(center);
		return center.getId();
	}
	
	private void saveVm(ObjectContent[] vmOcs){
		if (vmOcs!= null && vmOcs.length!= 0){
			for (ObjectContent oc: vmOcs){
				String[] props = new String[]{"name","parent",
		                "summary.config.memorySizeMB", "summary.config.numCpu"};
				try{
					Object[] values = this.getProperties(oc.getObj(),props);
					if (values!= null && values.length == props.length){
						//Save to DB
						com.wereach.vi.model.VirtualMachine machine = new com.wereach.vi.model.VirtualMachine(
						(String)values[0],(Integer)values[2],(Integer)values[3],"");
						this.vmDao.saveOrUpdate(machine);
					}		
				}catch(Exception e){
					logger.error("***Fail to save VM***"+e.getMessage(),e);
				}
			}
		}		
	}
	
	private void saveHostSystem(ObjectContent[] hostOcs,int dcId){
		if (hostOcs!= null && hostOcs.length!= 0){
			for (ObjectContent oc: hostOcs){
				String[] props = new String[]{"name","parent",
		                "summary.hardware.memorySize", "summary.hardware.numCpuCores",
		                "summary.hardware.cpuMhz", "summary.config.product.vendor",
		                "datastore","config.storageDevice"};
				try{
					Object[] values = this.getProperties(oc.getObj(),props);
					if (values!= null && values.length == props.length){
						//Save to DB
						com.wereach.vi.model.HostSystem hs = new com.wereach.vi.model.HostSystem(
								(String)values[5],(Long)values[2],(Short)values[3],
								(Integer)values[4],(String)values[0]);
						hs.setDcId(dcId);
						this.hostDao.saveOrUpdate(hs);
						
						Datastore[] stores = (Datastore[])values[6];
						if(stores!= null && stores.length!= 0){
							for (Datastore store: stores){
								DatastoreInfo info = store.getInfo();
								logger.info("***Datastore***"+"***freeSpace****"+info.getFreeSpace()
										+"***maxFileSize****"+info.getMaxFileSize()
										+"***url****"+info.getUrl());
								
								DataStore ds = new DataStore();
								ds.setName(store.getName());
								ds.setUrl(info.getUrl());
								ds.setFreeSpace(info.getFreeSpace());
								ds.setMaxFileSize(info.getMaxFileSize());
								ds.setDcId(dcId);
								ds.setHost(hs.getHostName());
								this.storeDao.saveOrUpdate(ds);
							}
						}
						
						HostStorageDeviceInfo hsdi = (HostStorageDeviceInfo)values[7];
						if (hsdi!= null){
							printStorageDeviceInfo(hsdi);
						}
					}		
				}catch(Exception e){
					logger.error("***Fail to save HostSystem***"+e.getMessage(),e);
				}
			}
		}			
		
	}
	
	private void saveDatastores(ObjectContent[] hostOcs,int dcId){
		if (hostOcs!= null && hostOcs.length!= 0){
			for (ObjectContent oc: hostOcs){
				String[] props = new String[]{"name","parent",
				         "info.freeSpace", "info.maxFileSize","info.url"};
				try{
					Object[] values = this.getProperties(oc.getObj(),props);
					if (values!= null && values.length == props.length){
						//Save to DB
						DataStore ds = new DataStore();
						ds.setName((String)values[0]);
						ds.setUrl((String)values[4]);
						ds.setFreeSpace((Long)values[2]);
						ds.setMaxFileSize((Long)values[3]);
						ds.setDcId(dcId);
						this.storeDao.saveOrUpdate(ds);
					}		
				}catch(Exception e){
					logger.error("***Fail to save HostSystem***"+e.getMessage(),e);
				}
			}
		}			
		
	}
	
	/*
	private void saveDataStore(Datastore store, int dcId, String host){		
		DatastoreInfo info = store.getInfo();
		logger.debug("freeSpace****"+info.getFreeSpace());
		logger.debug("maxFileSize****"+info.getMaxFileSize());
		logger.debug("url****"+info.getUrl());
		
		VirtualMachine[] vmArray = store.getVms();
		for (VirtualMachine vm : vmArray){
			this.saveVm(vm, 0);
		}
		
		DataStore ds = new DataStore();
		ds.setName(store.getName());
		ds.setUrl(info.getUrl());
		ds.setFreeSpace(info.getFreeSpace());
		ds.setMaxFileSize(info.getMaxFileSize());
		ds.setDcId(dcId);
		ds.setHost(host);
		this.storeDao.saveOrUpdate(ds);
	}
	
	
		
	private void travelVmFolder(Folder vmFolder){
		try{
			long begin = System.currentTimeMillis();
			ManagedEntity[] children = vmFolder.getChildEntity();			
			for (ManagedEntity me : children){
				if (me instanceof VirtualMachine){
					VirtualMachine vm = (VirtualMachine)me;
					this.saveVm(vm, 0);
				} else if (me instanceof Folder){
					Folder subFolder = (Folder)me;
					this.travelVmFolder(subFolder);
				}
			}
			long end = System.currentTimeMillis();
			logger.info("***Time elapsed for discover folder***"+vmFolder.getName()+"***"+(end-begin));
		}catch(Exception e){
			e.printStackTrace();
			logger.error(vmFolder+"***Fail to discover Folder***"+e.getMessage(),e);
		}
	}
	
	private void travelHostFolder(Folder hostFolder, int dcId){
		try{
			long begin = System.currentTimeMillis();
			ManagedEntity[] children = hostFolder.getChildEntity();
			for (ManagedEntity me : children){
				if (me instanceof ComputeResource){
					long begin1 = System.currentTimeMillis();
					ComputeResource cr = (ComputeResource)me;
					logger.debug("*******ComputeResource name****"+cr.getName());
					
					com.wereach.vi.model.ComputeResource res = new com.wereach.vi.model.ComputeResource();
					res.setName(cr.getName());
					if (cr instanceof ClusterComputeResource){
						res.setType(com.wereach.vi.model.ClusterComputeResource.RESOURCE_TYPE);
					}
					this.crDao.saveOrUpdate(res);
					
					HostSystem[] hostArr = cr.getHosts();
					for(HostSystem host : hostArr){
						String name = host.getName();
						logger.debug("*******host****"+name);
						HostListSummary summary = host.getSummary();
						logger.debug("*******host IP****"+summary.getManagementServerIp());
						HostHardwareSummary hardware = summary.getHardware();
						long memorySize = hardware.getMemorySize();
						logger.debug("*******memorySize****"+memorySize);
						short cpuCount = hardware.getNumCpuCores();
						logger.debug("*******numCpuCores****"+cpuCount);
						long cpuSpeedAvg = hardware.getCpuMhz();
						logger.debug("*******cpuMhz****"+cpuSpeedAvg);
						HostConfigSummary config = summary.getConfig();
						String vendor = config.getProduct().getVendor();
						logger.debug("*******vendor****"+vendor);
										
						com.wereach.vi.model.HostSystem hs = new com.wereach.vi.model.HostSystem(
								vendor,memorySize,cpuCount,cpuSpeedAvg,name);
						hs.setResId(res.getId());
						hs.setDcId(dcId);
						this.hostDao.saveOrUpdate(hs);
						VirtualMachine[] vmList = host.getVms();
						for (VirtualMachine vm : vmList){							
							this.saveVm(vm, hs.getId());
						}
						
						Datastore[] stores = host.getDatastores();
						for (Datastore store : stores){
							this.saveDataStore(store, 0, name);
						}
						
					}
					ResourcePool pool = cr.getResourcePool();
					logger.debug("*******pool****"+pool.getName());		
					VirtualMachine[] vmList = pool.getVMs();
					for (VirtualMachine vm : vmList){
						logger.debug("*******vm****"+vm.getName());
					}
					long end1 = System.currentTimeMillis();
					logger.info("***Time elapsed for discover ComputeResource***"+cr.getName()+"***"+(end1-begin1));
				} else if (me instanceof Folder){
					Folder subFolder = (Folder)me;
					this.travelHostFolder(subFolder, dcId);
				}
			}
			long end = System.currentTimeMillis();
			logger.info("***Time elapsed for discover host folder***"+hostFolder.getName()+"***"+(end-begin));
		}catch(Exception e){
			e.printStackTrace();
			logger.error("****Fail to discover host folder***"+e.getMessage(),e);
		}
	}*/

	public void unmanageDataCenter(String dataCenter) {
		// TODO Auto-generated method stub
		
	}

	public com.wereach.vi.model.HostSystem[] getAllHosts() {
		// TODO Auto-generated method stub
		return this.hostDao.getAllHosts().toArray(new com.wereach.vi.model.HostSystem[]{});
	}

	public DataCenter getAllInventory(String dataCenter) {
		// TODO Auto-generated method stub
		return this.dcDao.getDataCenterByName(dataCenter);
	}

	public com.wereach.vi.model.ClusterComputeResource[] getAllClusters() {
		// TODO Auto-generated method stub
		return this.crDao.getAllClusterComputeResources().toArray(new com.wereach.vi.model.ClusterComputeResource[]{});		
	}

	public DataStore[] getAllDataStores(String dataCenter) {
		// TODO Auto-generated method stub
		DataCenter dc = this.dcDao.getDataCenterByName(dataCenter);
		if (dc == null || dc.getDataStores() == null){
			return null;
		} else {
			return dc.getDataStores().toArray(new DataStore[]{});
		}				
	}

	public com.wereach.vi.model.VirtualMachine[] getAllVMs(String dataCenter) {
		// TODO Auto-generated method stub
		return this.vmDao.getAllVirtualMachines().toArray(new com.wereach.vi.model.VirtualMachine[]{} );
	}

	public DataStore[] getDataStoreByHost(String host) {
		// TODO Auto-generated method stub
		return this.storeDao.getDataStoreByHost(host).toArray(new DataStore[]{});
	}

	public com.wereach.vi.model.HostSystem[] getHostByCluster(String clusterName) {
		// TODO Auto-generated method stub
		com.wereach.vi.model.ComputeResource res = this.crDao.getComputeResourceByName(clusterName);
		return res.getHosts().toArray(new com.wereach.vi.model.HostSystem[]{});
	}

	public com.wereach.vi.model.HostSystem[] getHostByDataCenter(
			String dataCenter) {
		// TODO Auto-generated method stub
		return this.hostDao.getHostsByDataCenter(dataCenter).toArray(new com.wereach.vi.model.HostSystem[]{});
	}

	public com.wereach.vi.model.VirtualMachine[] getVMByHost(String hostName) {
		// TODO Auto-generated method stub
		com.wereach.vi.model.HostSystem host = this.hostDao.getHostSystemByName(hostName);
		if (host == null || host.getVms() == null){
			return null;
		} else {
			return host.getVms().toArray(new com.wereach.vi.model.VirtualMachine[]{});
		}
				
	}

	public PerfCounterInfo[] getCounters(String host) {
		// TODO Auto-generated method stub
		return this.getCouterByEntity("HostSystem", host);
	}
	
	 static void printPerfCounters(PerfCounterInfo[] pcis)
	  {
	    for(int i=0; pcis!=null && i<pcis.length; i++)
	    {
	    	logger.debug("\nKey:" + pcis[i].getKey());
	      String perfCounter = pcis[i].getGroupInfo().getKey() + "."
	          + pcis[i].getNameInfo().getKey() + "." 
	          + pcis[i].getRollupType();
	      logger.debug("PerfCounter:" + perfCounter);
	      logger.debug("Level:" + pcis[i].getLevel());
	      logger.debug("StatsType:" + pcis[i].getStatsType());
	      logger.debug("UnitInfo:" 
	          + pcis[i].getUnitInfo().getKey());
	    }
	  }
	public PerfCounterInfo[] getCountersByVm(String vm) {
		// TODO Auto-generated method stub	
		
		return this.getCouterByEntity("VirtualMachine", vm);
	}
	
	private PerfCounterInfo[] getCouterByEntity(String entityType,String entityName){
		/*try{
			ServiceInstance si = this.createServiceInstance(serverIp, username, password, ingoreCert);
			
			PerformanceManager perfMgr = si.getPerformanceManager();
			VirtualMachine entity = (VirtualMachine)new InventoryNavigator(si.getRootFolder()).searchManagedEntity(entityType,entityName);
		    
		    logger.debug(entityType+"***Print All Perf Counters for entity*******"+entityName);
		    PerfCounterInfo[] pcis = perfMgr.getPerfCounter();		    
		    List<PerfCounterInfo> pciList = Arrays.asList(pcis);
		    PerfMetricId[] ids = perfMgr.queryAvailablePerfMetric(entity, null,null,new Integer(300));
		    List<PerfMetricId> list = Arrays.asList(ids);		    
		    
		    for (Iterator<PerfCounterInfo> it = pciList.iterator();it.hasNext();){
		    	PerfCounterInfo pci = it.next();
		    	Integer id = new Integer(pci.getKey());
		    	if (!list.contains(id)){
		    		it.remove();
		    	} 
		    }
		    
		    PerfCounterInfo[] result  = pciList.toArray(new PerfCounterInfo[]{});		    
		    printPerfCounters(result);
		    si.getServerConnection().logout();
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return null;
	}

	public PerfEntityMetricBase[] getPMDataHistory(String host, int interval, int startTime, int duration, String groupName, String counterName) {
		try{		
			return this.getHistoryPMData("HostSystem", host, interval, startTime, duration, groupName, counterName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public PerfEntityMetricBase[] getPMDataHistoryByVm(String vm, int interval, int startTime, int duration, String groupName, String counterName) {
		// TODO Auto-generated method stub
		try{		
			return this.getHistoryPMData("VirtualMachine", vm, interval, startTime, duration, groupName, counterName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private PerfEntityMetricBase[] getHistoryPMData(String entityType,String entityName,int interval, int startTime, int duration, 
			String groupName, String counterName){
		PerfEntityMetricBase[] samples = null;
		/*ServiceInstance si = null;
		try{					
			si = this.createServiceInstance(serverIp, username, password, ingoreCert);
			ManagedEntity entity = new InventoryNavigator(
				      si.getRootFolder()).searchManagedEntity(entityType, entityName);	
			if (entity == null){
				logger.error("****Entity not found****"+entityName);
				return null;
			}
			
			PerformanceManager perfMgr = si.getPerformanceManager();
		   	 this.CounterInfo(perfMgr, entity);
		   	 
		   	 PerfInterval[] intervals = (PerfInterval[])perfMgr.getHistoricalInterval();;
		      boolean valid = checkInterval(intervals,interval);
		      if(!valid) {
		    	  logger.error("Invalid interval, Specify one from above");
		         return null;
		      }
	      
			 PerfCounterInfo pci = getCounterInfo(groupName,counterName,PerfSummaryType.average, null);
			if(pci == null) {
				logger.error("Incorrect Group Name and Countername specified");
				return null;
			}
			
			PerfQuerySpec qSpec = new PerfQuerySpec();
		      qSpec.setEntity(entity.getMOR());
		      qSpec.setMaxSample(new Integer(10));      
		      PerfQuerySpec[] qSpecs = new PerfQuerySpec[] {qSpec};
		      
		      Calendar sTime = si.currentTime();
		      Calendar eTime = si.currentTime();		      
		      eTime.add(Calendar.MINUTE, (duration - (2*startTime)));
		      sTime.add(Calendar.MINUTE, (duration - ((2*startTime)+duration)));		      
		      
		      logger.debug("Start Time " + sTime.getTime().toString());
		      logger.debug("End Time   " + eTime.getTime().toString());
		      
		      PerfMetricId[] aMetrics 
		         = perfMgr.queryAvailablePerfMetric(entity,sTime,eTime,interval);
		      PerfMetricId ourCounter = null;
		      
		      for(int index=0; index<aMetrics.length; ++index) {
		         if(aMetrics[index].getCounterId() == pci.getKey()) {
		            ourCounter = aMetrics[index];
		            break;
		         }
		      }
		      if(ourCounter == null) {
		         logger.warn("No data on enttiy to collect. "+entityName);
		      }else {
		    	 qSpec = new PerfQuerySpec();
		         qSpec.setEntity(entity.getMOR());
		         qSpec.setStartTime(sTime);
		         qSpec.setEndTime(eTime);
		         qSpec.setMetricId(new PerfMetricId[]{ourCounter});
		         qSpec.setIntervalId(interval);           
		         qSpecs = new PerfQuerySpec[] {qSpec};
		         
		          samples = perfMgr.queryPerf(qSpecs);
		         if(samples != null) {
		            displayValues(samples, pci, ourCounter, interval);
		         }
		         else {
		        	 logger.debug("No Samples Found");
		         }
		       }      
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (si!= null){
				si.getServerConnection().logout();
			}
		}*/
		return samples;
	}
	
	private boolean checkInterval(PerfInterval [] intervals, Integer interval) 
	    throws Exception {
		 boolean flag = false;
		 for(int i=0; i<intervals.length; ++i) {
		    PerfInterval pi = intervals[i];
		    if(pi.getSamplingPeriod() == interval){
		       flag = true;
		       break;
		    }
		 }
		 if(!flag){
		    logger.debug("Available summary collection intervals");
		    logger.debug("Period\tLength\tName");
		    for(int i=0; i<intervals.length; ++i) {
		       PerfInterval pi = intervals[i];
		       logger.debug(pi.getSamplingPeriod()+"\t"
		                         +pi.getLength()+"\t"+pi.getName());
		    }		   
		 }
		 return flag;
	}
	
	private PerfCounterInfo getCounterInfo(String groupName, 
            String counterName,
            PerfSummaryType rollupType, 
            PerfStatsType statsType) {
		ArrayList counters = getCounterInfos(groupName, counterName);
		if(counters != null) {
			for(Iterator i=counters.iterator(); i.hasNext();) {
				PerfCounterInfo pci = (PerfCounterInfo)i.next();
				if((statsType == null  || statsType.equals(pci.getStatsType())) &&
				(rollupType == null || rollupType.equals(pci.getRollupType()))) {
				return pci;
				}
			}
		}
		return null;
	} 
	
	private ArrayList getCounterInfos(String groupName, String counterName) {
	      Map nameMap = (Map)_pci.get(groupName);
	      if(nameMap != null) {
	         ArrayList ret = (ArrayList)nameMap.get(counterName);
	         if(ret != null) {
	            return new ArrayList(ret);
	         }
	      }
	      return null;
	   } 

	/* private void CounterInfo(PerformanceManager perfMgr,ManagedEntity entity)  throws Exception {
	      PerfCounterInfo[] cInfos 
	         = (PerfCounterInfo[])perfMgr.getPerfCounter();
	      for(int i=0; i<cInfos.length; ++i) {
	         PerfCounterInfo cInfo = cInfos[i]; 
	         String group = cInfo.getGroupInfo().getKey();
	         Map nameMap = null;
	         if(!_pci.containsKey(group)) {
	            nameMap = new HashMap();
	            _pci.put(group, nameMap);
	         } else {
	            nameMap = (Map)_pci.get(group);
	         }
	         String name = cInfo.getNameInfo().getKey();
	         ArrayList counters = null;
	         if(!nameMap.containsKey(name)) {
	            counters = new ArrayList();
	            nameMap.put(name, counters);
	         } else {
	            counters = (ArrayList)nameMap.get(name);
	         }
	         counters.add(cInfo);
	      }
	   }
	 */
	public PerfEntityMetricBase[] getPMDataRealTime(String host) {
		// TODO Auto-generated method stub
		/*try{		
			ServiceInstance si = this.createServiceInstance(serverIp, username, password, ingoreCert);
			ManagedEntity h = new InventoryNavigator(
				      si.getRootFolder()).searchManagedEntity(
				        "HostSystem", host);		    			      
		   	return this.getPmDataByEntity(si, h,null,null, 0);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return null;
	}

	public PerfEntityMetricBase[] getPMDataRealTimeByVm(String vm) {
		// TODO Auto-generated method stub
		/*try{		
			ServiceInstance si = this.createServiceInstance(serverIp, username, password, ingoreCert);
			ManagedEntity machine = new InventoryNavigator(
				      si.getRootFolder()).searchManagedEntity(
				        "VirtualMachine", vm);	    			      
		   		    
		    
			return this.getPmDataByEntity(si, machine,null,null, 0);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return null;
	}
	
	/*private PerfEntityMetricBase[] getPmDataByEntity(ServiceInstance si,ManagedEntity entity,
			Calendar startTime,Calendar endTime, int interval){
		try{
			
			PerformanceManager perfMgr = si.getPerformanceManager();
			
			logger.debug(entity.getClass()+"***Print Perf Real time data for entity****"+entity.getName());
		    
		 // find out the refresh rate for the entity
			if (interval == 0){
				PerfProviderSummary pps = perfMgr.queryPerfProviderSummary(entity);
				interval = pps.getRefreshRate().intValue();
			}		    
	
		    // retrieve all the available perf metrics for entity
		    PerfMetricId[] pmis = perfMgr.queryAvailablePerfMetric(
		    		entity, startTime, endTime, interval);
	
		    PerfQuerySpec qSpec = createPerfQuerySpec(
		    		entity, pmis, 3, interval);
	
		    
		    PerfEntityMetricBase[] pValues = perfMgr.queryPerf(
		        new PerfQuerySpec[] {qSpec});
	      if(pValues != null)
	      {
	        displayValues(pValues);
	      }	      
	      si.getServerConnection().logout();
	      return pValues;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}*/

	  /*static PerfQuerySpec createPerfQuerySpec(ManagedEntity me, 
	      PerfMetricId[] metricIds, int maxSample, int interval)
	  {
	    PerfQuerySpec qSpec = new PerfQuerySpec();
	    qSpec.setEntity(me.getMOR());
	    // set the maximum of metrics to be return
	    // only appropriate in real-time performance collecting
	    qSpec.setMaxSample(new Integer(maxSample));
	    qSpec.setMetricId(metricIds);
	    // optionally you can set format as "normal"
	    qSpec.setFormat("csv");
	    // set the interval to the refresh rate for the entity
	    qSpec.setIntervalId(new Integer(interval));
	 
	    return qSpec;
	  }*/

	  static void displayValues(PerfEntityMetricBase[] values)
	  {
	    for(int i=0; i<values.length; ++i) 
	    {
	      String entityDesc = values[i].getEntity().getType() 
	          + ":" + values[i].getEntity().get_value();
	      logger.debug("Entity:" + entityDesc);
	      if(values[i] instanceof PerfEntityMetric)
	      {
	        printPerfMetric((PerfEntityMetric)values[i]);
	      }
	      else if(values[i] instanceof PerfEntityMetricCSV)
	      {
	        printPerfMetricCSV((PerfEntityMetricCSV)values[i]);
	      }
	      else
	      {
	    	  logger.debug("UnExpected sub-type of " +
	        		"PerfEntityMetricBase.");
	      }
	    }
	  }
	  
	  static void printPerfMetric(PerfEntityMetric pem)
	  {
	    PerfMetricSeries[] vals = pem.getValue();
	    PerfSampleInfo[]  infos = pem.getSampleInfo();
	    
	    logger.debug("Sampling Times and Intervales:");
	    for(int i=0; infos!=null && i <infos.length; i++)
	    {
	    	logger.debug("Sample time: " 
	          + infos[i].getTimestamp().getTime());
	    	logger.debug("Sample interval (sec):" 
	          + infos[i].getInterval());
	    }
	    logger.debug("Sample values:");
	    for(int j=0; vals!=null && j<vals.length; ++j)
	    {
	    	logger.debug("Perf counter ID:" 
	          + vals[j].getId().getCounterId());
	    	logger.debug("Device instance ID:" 
	          + vals[j].getId().getInstance());
	      
	      if(vals[j] instanceof PerfMetricIntSeries)
	      {
	        PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
	        long[] longs = val.getValue();
	        for(int k=0; k<longs.length; k++) 
	        {
	        	logger.debug(longs[k] + " ");
	        }
	        logger.debug("Total:"+longs.length);
	      }
	      else if(vals[j] instanceof PerfMetricSeriesCSV)
	      { // it is not likely coming here...
	        PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) vals[j];
	        logger.debug("CSV value:" + val.getValue());
	      }
	    }
	  }
	  static void printPerfMetricCSV(PerfEntityMetricCSV pems)
	  {
		  logger.debug("SampleInfoCSV:" 
	        + pems.getSampleInfoCSV());
	    PerfMetricSeriesCSV[] csvs = pems.getValue();
	    for(int i=0; i<csvs.length; i++)
	    {
	    	logger.debug("PerfCounterId:" 
	          + csvs[i].getId().getCounterId());
	    	logger.debug("CSV sample values:" 
	          + csvs[i].getValue());
	    }
	  }
	  
	  private void displayValues(PerfEntityMetricBase[] values, 
              PerfCounterInfo pci, 
              PerfMetricId pmid,
              Integer interval) {
			for(int i=0; i<values.length; ++i) {
				PerfMetricSeries[] vals = ((PerfEntityMetric)values[i]).getValue();
				PerfSampleInfo[]  infos = ((PerfEntityMetric)values[i]).getSampleInfo();
				if (infos == null || infos.length == 0) {
				logger.debug("No Samples available. Continuing.");
				continue;
				}
				logger.debug("Sample time range: " 
				           + infos[0].getTimestamp().getTime().toString() + " - " +
				            infos[infos.length-1].getTimestamp().getTime().toString() +
				            ", read every "+interval+" seconds");
				for(int vi=0; vi<vals.length; ++vi) {
				if(pci != null) {
				if(pci.getKey() != vals[vi].getId().getCounterId())
				continue;
				logger.debug(pci.getNameInfo().getSummary() 
				                + " - Instance: " + pmid.getInstance());
				}
				if(vals[vi] instanceof PerfMetricIntSeries) {
				PerfMetricIntSeries val = (PerfMetricIntSeries)vals[vi];
				long[] longs = val.getValue();
				for(int k=0; k<longs.length; ++k) {
					logger.debug(longs[k] + " ");
				}				
				}
				}
			}
		}
}
