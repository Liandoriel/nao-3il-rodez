package cci.plugin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import cci.serveur.ServeurBean;

import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.Object;

@ManagedBean
public class Audio implements Plugin{
	/*
	 * Variable de connexion
	 */
	@ManagedProperty(value="#{serveur}")
	private ServeurBean serveur;
	
	public ServeurBean getServeur() {
		return serveur;
	}
	public void setServeur(ServeurBean serveur) {
		this.serveur = serveur;
	}
	
	/*
	 * Variable du module audio 
	 */
	private int volume;
	private boolean muet;
	
	public int getVolume() {
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALAudioDevice");
			Future<java.lang.Object> volObj = adp.call("getOutputVolume");
			
			if(volObj != null){
				volume = (int)volObj.get();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return volume;
	}
	public void setVolume(int volume) {
		if(volume >= 0 && volume <= 100){
			this.volume = volume;
		}
	}
	public boolean isMuet() {
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALAudioDevice");
			Future<java.lang.Object> muetObj = adp.call("isAudioOutMuted");
			
			if(muetObj != null){
				muet = (boolean)muetObj.get();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return muet;
	}
	
	/*
	 * Methode du module Audio
	 */
	@Override
	public String lancerModule() throws Exception{
		Session session = new Session();
		Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
		fut.get();

		Object tts = null;
		tts = session.service("ALTextToSpeech");
		tts.call("say", "Bonjour");
	
		if(session != null && session.isConnected()){
			session.close();
		}
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
	
	public String modifierVolume(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALAudioDevice");
			adp.call("setOutputVolume", volume);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return null;
	}
	
	public String modifierMuet(){
		// on inverse la mutation
		muet = !muet;
		
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALAudioDevice");
			adp.call("muteAudioOut", muet);
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
