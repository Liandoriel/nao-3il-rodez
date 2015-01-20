package cci.data;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class FsrData {

	private String couleurG;
	private String couleurD;
	
	public String getCouleurG() {
		return couleurG;
	}

	public void setCouleurG(String couleurG) {
		this.couleurG = couleurG;
	}

	public String getCouleurD() {
		return couleurD;
	}

	public void setCouleurD(String couleurD) {
		this.couleurD = couleurD;
	}
	
	private String fsrDroit;
	private String fsrGauche;
	
	private String poidsDroit;
	private String poidsGauche;
	
	public FsrData(){
		fsrDroit = "";
		fsrGauche = "";
		
		poidsDroit = "";
		poidsGauche = "";
	}
	
	public FsrData(String fsrD, String fsrG, String poidsD, String poidsG){
		fsrDroit = fsrD;
		fsrGauche = fsrG;
		
		poidsDroit = poidsD;
		poidsGauche = poidsG;
		
		try{
			if(Float.parseFloat(fsrD) < 1){
				couleurD = "FSRtouchePas";
			}else{
				couleurD = "FSRtouche";
			}
			
			if(Float.parseFloat(fsrG) < 1){
				couleurG = "FSRtouchePas";
			}else{
				couleurG = "FSRtouche";
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			couleurD = "FSRtouche";
			couleurG = "FSRtouche";
		}
		
	}
	
	public String getFsrDroit() {
		return fsrDroit;
	}
	public void setFsrDroit(String fsrDroit) {
		this.fsrDroit = fsrDroit;
	}
	public String getFsrGauche() {
		return fsrGauche;
	}
	public void setFsrGauche(String fsrGauche) {
		this.fsrGauche = fsrGauche;
	}
	public String getPoidsDroit() {
		return poidsDroit;
	}
	public void setPoidsDroit(String poidsDroit) {
		this.poidsDroit = poidsDroit;
	}
	public String getPoidsGauche() {
		return poidsGauche;
	}
	public void setPoidsGauche(String poidsGauche) {
		this.poidsGauche = poidsGauche;
	}
	
	
}
