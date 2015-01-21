package cci.plugin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Object;
import com.aldebaran.qimessaging.Session;

import cci.data.JointData;
import cci.serveur.ServeurBean;

@ManagedBean
public class Actionneur implements Plugin {

	@ManagedProperty(value="#{serveur}")
	private ServeurBean serveur;
	
	private List<JointData> listJoint;
	
	public List<JointData> getListJoint() {
		listJoint = new ArrayList<JointData>();
		
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALMotion");
			Future<java.lang.Object> cptObj = adp.call("getBodyNames", "Joints");
			
			if(cptObj != null){
				@SuppressWarnings( { "unchecked", "unused" } )
				List<String> ret = (List<String>) cptObj.<List<String>>get();
				for (String value : ret) {
					Future<java.lang.Object> cptData = adp.call("getAngles", value, false);
					Future<java.lang.Object> cptLim = adp.call("getLimits", value);
					@SuppressWarnings("unchecked")
					List<Float> resF = (List<Float>) cptData.get();
					@SuppressWarnings("unchecked")
					List<java.lang.Object> resL = (List<java.lang.Object>) cptLim.get();
					@SuppressWarnings("unchecked")
					ArrayList<Float> limites = (ArrayList<Float>) resL.get(0);
					
					Float angle = (float)Math.round((((resF.get(0) * 180)/Math.PI) * 100) / 100);
					Float min = (float)Math.round((((limites.get(0) * 180)/Math.PI) * 100) / 100);
					Float max = (float)Math.round((((limites.get(1) * 180)/Math.PI) * 100) / 100);
					
					listJoint.add(new JointData(value, angle.toString(), min.toString(), max.toString()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		
		return listJoint;
	}
	public void setListJoint(List<JointData> listJoint) {
		this.listJoint = listJoint;
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
	
	public String ouvrirMain(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALMotion");
			adp.call("openHand", "RHand");
			adp.call("openHand", "LHand");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return null;
	}
	
	public String fermerMain(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALMotion");
			adp.call("closeHand", "RHand");
			adp.call("closeHand", "LHand");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return null;		
	}
	
	public String debout(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALRobotPosture");
			adp.call("goToPosture", "Stand", 1.0);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isConnected()){
				session.close();
			}
		}
		return null;		
	}
	
	public String assis(){
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			Object adp = null;
			adp = session.service("ALRobotPosture");
			adp.call("goToPosture", "Sit", 1.0);
			
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
