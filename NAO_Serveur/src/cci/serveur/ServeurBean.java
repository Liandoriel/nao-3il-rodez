package cci.serveur;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="serveur")
@ApplicationScoped
public class ServeurBean {
	private String ipNAO = null;
	private int portNAO = 9559;
	
	public String getIpNAO() {
		return ipNAO;
	}
	public void setIpNAO(String ipNAO) {
		this.ipNAO = ipNAO;
	}
	public int getPortNAO() {
		return portNAO;
	}
	public void setPortNAO(int portNAO) {
		this.portNAO = portNAO;
	}
	 
	
	public boolean isSetting(){
		if(ipNAO == null || portNAO == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public String connect(){
		return null;
	}
	
	public String disconnect(){
		ipNAO = null;
		portNAO = 0;
		return null;
	}
	
}
