package cci.data;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class JointData {

	public String getNomJoint() {
		return nomJoint;
	}
	public String getAngle() {
		return angle;
	}
	public void setNomJoint(String nomJoint) {
		this.nomJoint = nomJoint;
	}
	public void setAngle(String angle) {
		this.angle = angle;
	}
	private String nomJoint;
	private String angle;
	
	public JointData(){
		nomJoint = "";
		angle = "";
	}
	
	public JointData(String nom, String angle){
		nomJoint = nom;
		this.angle = angle;
	}
}
