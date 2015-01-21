package cci.plugin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import cci.serveur.ServeurBean;

import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Object;
import com.aldebaran.qimessaging.Session;


@ManagedBean
public class Comportement implements Plugin{
	
	/*
	 * Variable de connexion
	 */
	@ManagedProperty(value="#{serveur}")
	private ServeurBean serveur;
	
	/*
	 * 
	 */
	private List<String> cpt;
	private String comp;
	
	public String getComp() {
		return comp;
	}
	public void setComp(String comp) {
		this.comp = comp;
	}
	public ServeurBean getServeur() {
		return serveur;
	}
	public void setServeur(ServeurBean serveur) {
		this.serveur = serveur;
	}
	
	public List<String> getCpt() {
		Session session = null;
		cpt = new ArrayList<String>();
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALBehaviorManager");
			Future<java.lang.Object> cptObj = adp.call("getInstalledBehaviors");
			
			if(cptObj != null){
				@SuppressWarnings({ "unchecked", "unused" })
				List<String> ret = (List<String>) cptObj.<List<String>>get();
				for (String value : ret) {
					if(!value.contains("animation") && !value.contains("/")){
						cpt.add(value);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return cpt;
	}
	public void setCpt(List<String> cpt) {
		this.cpt = cpt;
	}
	
	@Override
	public String lancerModule() {
		// TODO Auto-generated method stub
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
		if(comp != null && comp.trim() != ""){
			Session session = null;
			try{
				session = new Session();
				Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
				fut.get();
				
				Object adp = null;
				adp = session.service("ALBehaviorManager");
				adp.call("startBehavior", comp);
				comp = null;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(session != null && session.isConnected()){
					session.close();
				}
			}
		}
		return null;
	}
	
	public String stopComportement(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALBehaviorManager");
			adp.call("stopAllBehaviors");
			
			// on regarde si le comportement est installé
			Future<java.lang.Object> obj = adp.call("isBehaviorLoaded", "securite_Robot");
			
			if(obj != null){
				boolean isInstalled = (boolean)obj.get();
				if(isInstalled){
					adp.call("startBehavior", "securite_Robot");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return null;
	}
}
