package cci.plugin;

import java.util.ArrayList;
import java.util.List;

import cci.data.CapteurData;
import cci.data.FsrData;
import cci.serveur.ServeurBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Object;
import com.aldebaran.qimessaging.Session;

@ManagedBean
public class Capteur implements Plugin {

	@ManagedProperty(value="#{serveur}")
	private ServeurBean serveur;
	
	private List<CapteurData> cptList;
	private FsrData fsr;
	
	public List<CapteurData> getCptList() {
		Session session = null;
		cptList = new ArrayList<CapteurData>();
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALMemory");
			Future<java.lang.Object> cptObj = adp.call("getDataList", "Temperature/Sensor/Value");
			
			if(cptObj != null){
				@SuppressWarnings({ "unchecked", "unused" })
				List<String> ret = (List<String>) cptObj.<List<String>>get();
				for (String value : ret) {
					Future<java.lang.Object> cptData = adp.call("getData", value);
					String res = cptData.get().toString();
					
					cptList.add(new CapteurData(value.replace("/Temperature/Sensor/Value", "").replace("Device/SubDeviceList/", ""), res));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return cptList;
	}
	public ServeurBean getServeur() {
		return serveur;
	}
	public void setServeur(ServeurBean serveur) {
		this.serveur = serveur;
	}
	public void setCptList(List<CapteurData> cptList) {
		this.cptList = cptList;
	}
	public FsrData getFsr() {
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALMemory");
			// FSR
			Future<java.lang.Object> data1 = adp.call("getData", "leftFootContact");
			Future<java.lang.Object> data2 = adp.call("getData", "rightFootContact");
			// Poids
			Future<java.lang.Object> data3 = adp.call("getData", "leftFootTotalWeight");
			Future<java.lang.Object> data4 = adp.call("getData", "rightFootTotalWeight");
			
			fsr = new FsrData((String)data2.get().toString(), (String)data1.get().toString(), (String)data4.get().toString(), (String)data3.get().toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return fsr;
	}
	public void setFsr(FsrData fsr) {
		this.fsr = fsr;
	}
	@Override
	public String lancerModule(){
		
		return null;
	}
	@Override
	public String refreshModule() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String commande() {
		// TODO Auto-generated method stub
		return null;
	}
}
