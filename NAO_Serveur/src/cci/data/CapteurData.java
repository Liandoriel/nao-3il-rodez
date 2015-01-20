package cci.data;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class CapteurData {
	private String nom;
	private String couleur;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
		
		if(Float.parseFloat(valeur) > 60){
			setCouleur("tempRouge");
		}else if(Float.parseFloat(valeur) > 45){
			setCouleur("tempOrange");
		}else{
			setCouleur("tempVert");
		}
	}
	private String valeur;
	
	public CapteurData(){
		nom = "";
		valeur = "";
	}
	
	public CapteurData(String arg0, String arg1){
		nom = arg0;
		valeur = arg1;
		
		if(Float.parseFloat(valeur) > 60){
			setCouleur("tempRouge");
		}else if(Float.parseFloat(valeur) > 45){
			setCouleur("tempOrange");
		}else{
			setCouleur("tempVert");
		}
	}
	public String getCouleur() {
		return couleur;
	}
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
}
