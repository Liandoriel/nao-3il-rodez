package cci.plugin;

import java.awt.image.BufferedImage;
import java.util.List;
import java.io.File;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.imageio.ImageIO;

import cci.serveur.ServeurBean;

import com.aldebaran.qimessaging.*;
import com.aldebaran.qimessaging.helpers.al.*;

@ManagedBean
public class Camera implements Plugin {
	
	@ManagedProperty(value="#{serveur}")
	private ServeurBean serveur;
	
	public ServeurBean getServeur() {
		return serveur;
	}
	public void setServeur(ServeurBean serveur) {
		this.serveur = serveur;
	}
	
	private String imgCam;
	
	
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
	public String getImgCam() {
		Session session = null;
		try{
			session = new Session();
			Future<Void> fut = session.connect("tcp://"+ serveur.getIpNAO() + ":" + serveur.getPortNAO() );
			fut.get();
			
			ALVideoDevice vd = new ALVideoDevice(session);
			vd.subscribeCamera("Projet_NAO", 0, 0, 11, 15);
			List<java.lang.Object> imgObj = (List<java.lang.Object>)vd.getImageRemote("Projet_NAO");
			vd.unsubscribe("Projet_NAO");
			
			// Traitement de l'image
			if(imgObj != null && imgObj.size() >= 7){
				byte[] byteImg = (byte[]) imgObj.get(6);
				
				int x = 0;
				int y = 0;
				
				BufferedImage img = new BufferedImage(160, 120, BufferedImage.TYPE_INT_RGB);
				
				for(int i = 0; i < byteImg.length; i = i + 3){
					if(x == 160){
						x = 0;
						y++;
					}
					int rgb = ((int)byteImg[i])*65536 + ((int)byteImg[i+1])*256 +((int)byteImg[i+2]);
					img.setRGB(x, y, rgb);
					
					x++;
				}
				
				img.flush();
				File out = new File("C:/cam.png");
				ImageIO.write(img, "png", out);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return imgCam;
	}
	public void setImgCam(String imgCam) {
		this.imgCam = imgCam;
	}
}
