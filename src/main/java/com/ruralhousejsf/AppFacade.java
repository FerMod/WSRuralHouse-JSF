package com.ruralhousejsf;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.ApplicationFacadeFactory;
import businessLogic.ApplicationFacadeImpl;
import businessLogic.ApplicationFacadeInterface;
import configuration.Config;
import configuration.ConfigXML;

public class AppFacade implements Serializable {	

	private volatile static AppFacade instance;

	private final static String DEFAULT_CONFIG_FILEPATH = "/db/config.xml";
	private static Config config;
	private static ApplicationFacadeInterface aplicationFacade;

	private AppFacade(){		
		this(config);
	}

	private AppFacade(Config config){

		if (instance != null){
			throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
		}

		AppFacade.config = config != null ? config : ConfigXML.loadConfig(AppFacade.class.getResource(DEFAULT_CONFIG_FILEPATH).getFile());
		AppFacade.aplicationFacade = createApplicationFacade(config);

	}

	private static ApplicationFacadeInterface createApplicationFacade(Config config2) {
		
		ApplicationFacadeInterface afi = null;
		
		if (config.isBusinessLogicLocal()) {
			
			afi = new ApplicationFacadeImpl();
			
		} else { //Si es remoto

			//String serviceName = "http://localhost:9999/ws/ruralHouses?wsdl";
			String serviceName= "http://"+config.getBusinessLogicNode() +":"+ config.getBusinessLogicPort()+"/ws/"+config.getBusinessLogicName()+"?wsdl";

			//URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
			URL url = null;
			try {
				url = new URL(serviceName);


				//1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
				QName qname = new QName("http://businessLogic/", "FacadeImplementationWSService");

				Service service = Service.create(url, qname);

				afi = service.getPort(ApplicationFacadeInterface.class);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		} 
		
		return afi;
	}

	/**
	 * Ensure that nobody can create another instance by serializing and deserializing the singleton.
	 * @return the current singleton instance
	 * @see Serializable
	 */
	protected AppFacade readResolve() {
		return getInstance();
	}

	/**
	 * Return the current instance of the object. <br>
	 * Creates a new instance if none exists.
	 * 
	 * @return the current or new instance of this object
	 */
	public synchronized static AppFacade getInstance() {
		if(instance == null) {
			synchronized (AppFacade.class) {
				if(instance == null) instance = new AppFacade();
			}
		}
		return instance;
	}

	/**
	 * Loads the configuration file and return a new instance of this object with the configuration loaded.
	 * 
	 * @param filePath the configuration file path
	 * @return the new instance with the loaded configuration
	 */
	public synchronized static AppFacade loadConfig(Config config) {
		AppFacade.config = config;
		return getInstance();
	}

	public ApplicationFacadeInterface getImpl() {
		
		if(aplicationFacade == null) {

			if (config.isBusinessLogicLocal()) {
				aplicationFacade = new ApplicationFacadeImpl();
			} else {
				//Es remoto
				//String serviceName = "http://localhost:9999/ws/ruralHouses?wsdl";
				String serviceName = "http://" + config.getBusinessLogicNode() + ":" + config.getBusinessLogicPort() + "/ws/" + config.getBusinessLogicName() + "?wsdl";

				//URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
				URL url = null;
				try {
					url = new URL(serviceName);


					//1st argument refers to wsdl document above
					//2nd argument is service name, refer to wsdl document above
					QName qname = new QName("http://businessLogic/", "FacadeImplementationWSService");

					Service service = Service.create(url, qname);

					aplicationFacade = service.getPort(ApplicationFacadeInterface.class);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

			} 

		}
		
		return aplicationFacade;
	}

	/**
	 * Auto-generated serial version ID
	 */
	private static final long serialVersionUID = -2628567254888914298L;

}
