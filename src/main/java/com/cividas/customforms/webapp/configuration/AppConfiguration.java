package com.cividas.customforms.webapp.configuration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ManagedBean(name="appConfiguration", eager=true)
@ApplicationScoped
public class AppConfiguration implements Serializable{
	
	private static final long serialVersionUID = -5902063804231628182L;

	private static final Logger log = LoggerFactory.getLogger(AppConfiguration.class);
	
	protected static final String WEB_CONFIGURATION_FILE_PARAMETER_IN_WEB_XML = "App.Configuration.Path";
	
	private Configuration config;
	//[INI][CE-1024] IFrame base para el detalle de los expedientes
	private String webBaseURI; 
	
	private ArrayList<String> iconFileNames;
	//[FIN][CE-1024] IFrame base para el detalle de los expedientes

	public AppConfiguration (){
		try {
			String path = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(WEB_CONFIGURATION_FILE_PARAMETER_IN_WEB_XML);
			PropertiesConfiguration propConfig;
			propConfig = new PropertiesConfiguration(path);
			propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
			config = propConfig;
		} catch (ConfigurationException e) {
			log.error("Error cargando la configuración", e);
		}
	}

    protected Configuration getConfig(){
        return config;
    }
    
    
    public String getRestBaseURI(){
    	String baseUri = null;
    	if (getConfig() != null){
		    baseUri = getConfig().getString("rest.uri");		
			if (baseUri != null && baseUri.endsWith("/")){
				baseUri = baseUri.substring(0, baseUri.length() - 1);
			}
    	}
		return baseUri;
    }
  //[INI][CE-1024] IFrame base para el detalle de los expedientes
    public String getWebBaseURI(){
    	String webBaseURI = null;
    	if (getConfig() != null){
    		webBaseURI = getConfig().getString("web.uri");		
			if (webBaseURI != null && webBaseURI.endsWith("/")){
				webBaseURI = webBaseURI.substring(0, webBaseURI.length() - 1);
			}
    	}
		return webBaseURI;
    }
    
    public List<String> getIconFileNames(){
		if (iconFileNames == null) {
			iconFileNames = new ArrayList<String>();
			File folder = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/icons/format/"));
			File[] listOfFiles = folder.listFiles();
			
			if (listOfFiles!=null){
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						iconFileNames.add(listOfFiles[i].getName());
					}
				}
			}
		}
		
		return iconFileNames;
		
	}
  //[FIN][CE-1024] IFrame base para el detalle de los expedientes
}
