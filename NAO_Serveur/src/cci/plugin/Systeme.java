package cci.plugin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import cci.serveur.ServeurBean;

import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Object;
import com.aldebaran.qimessaging.Session;

@ManagedBean
public class Systeme implements Plugin{

	/*
	 * Variable de connexion
	 */
	@ManagedProperty(value="#{serveur}")
	private ServeurBean serveur;
	
	/*
	 * variable du module systeme
	 */
	private int batterie;
	private String imgBatterie = "batterieVeryBad.png";
	private String version;
	private String name;
	
	public int getBatterie() {
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALBattery");
			Future<java.lang.Object> batObj = adp.call("getBatteryCharge");
			
			if(batObj != null){
				batterie = (int)batObj.get();
				
				if(batterie >= 75){
					imgBatterie = "batterieVeryGood.png";
				}else if(batterie > 50){
					imgBatterie = "batterieGood.png";
				}else if(batterie > 25){
					imgBatterie = "batterieBad.png";
				}else{
					imgBatterie = "batterieVeryBad.png";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return batterie;
	}
	public void setBatterie(int batterie) {
		this.batterie = batterie;
	}
	
	public String getVersion() {
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALSystem");
			Future<java.lang.Object> versObj = adp.call("systemVersion");
			
			if(versObj != null){
				version = (String)versObj.get();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getName(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALSystem");
			Future<java.lang.Object> nameObj = adp.call("robotName");
			
			if(nameObj != null){
				name = (String)nameObj.get();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	public ServeurBean getServeur() {
		return serveur;
	}
	public void setServeur(ServeurBean serveur) {
		this.serveur = serveur;
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
	public String getImgBatterie() {
		return imgBatterie;
	}
	public void setImgBatterie(String imgBatterie) {
		this.imgBatterie = imgBatterie;
	}
}
