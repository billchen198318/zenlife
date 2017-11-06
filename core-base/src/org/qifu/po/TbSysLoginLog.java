package org.qifu.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.qifu.base.model.BaseEntity;
import org.qifu.base.model.EntityPK;
import org.qifu.util.SimpleUtils;

@Entity
@Table(name="tb_sys_login_log")
public class TbSysLoginLog extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 4185884842847417329L;
	private String oid;
	private String user;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;	
	
	@Transient
	public String getCdateString() {
		return SimpleUtils.getDateFormat_yyyyMMddHHmmss(this.cdate);
	}	
	
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
	
	@Column(name="USER")
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
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
