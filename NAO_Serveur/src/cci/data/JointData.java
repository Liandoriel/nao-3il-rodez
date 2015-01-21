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
	private String min;
	
	public String getMin() {
		return min;
	}
	public String getMax() {
		return max;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public void setMax(String max) {
		this.max = max;
	}
	private String max;
	
	public JointData(){
		nomJoint = "";
		angle = "";
	}
	
	public JointData(String nom, String angle, String min, String max){
		nomJoint = nom;
		this.angle = angle;
		this.min = min;
		this.max = max;
	}
}
