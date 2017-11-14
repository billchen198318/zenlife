package org.qifu.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.qifu.base.model.BaseEntity;
import org.qifu.base.model.EntityPK;
import org.qifu.base.model.EntityUK;

@Entity
@Table(
		name="zl_blood_pressure", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PERSON_ID", "LOG_DATE"} ) 
		} 
)
public class ZlBloodPressure extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 6736720619663693305L;
	
	private String oid;
	private String personId;
	private String logDate;
	private int sbp;
	private int dbp;
	private int fulse;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;		

	@Override
	@Id
	@EntityPK(name="oid")
	@Column(name="OID")
	public String getOid() {
		return oid;
	}
	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}		
	
	@EntityUK(name="personId")
	@Column(name="PERSON_ID")	
	public String getPersonId() {
		return personId;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@EntityUK(name="logDate")
	@Column(name="LOG_DATE")	
	public String getLogDate() {
		return logDate;
	}
	
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	
	@Column(name="SBP")
	public int getSbp() {
		return sbp;
	}
	
	public void setSbp(int sbp) {
		this.sbp = sbp;
	}
	
	@Column(name="DBP")
	public int getDbp() {
		return dbp;
	}
	
	public void setDbp(int dbp) {
		this.dbp = dbp;
	}
	
	@Column(name="FULSE")
	public int getFulse() {
		return fulse;
	}
	
	public void setFulse(int fulse) {
		this.fulse = fulse;
	}
	
	@Override
	@Column(name="CUSERID")
	public String getCuserid() {
		return this.cuserid;
	}
	@Override
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	@Override
	@Column(name="CDATE")
	public Date getCdate() {
		return this.cdate;
	}
	@Override
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	@Override
	@Column(name="UUSERID")
	public String getUuserid() {
		return this.uuserid;
	}
	@Override
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
	}
	@Override
	@Column(name="UDATE")
	public Date getUdate() {
		return this.udate;
	}
	@Override
	public void setUdate(Date udate) {
		this.udate = udate;
	}		

}
