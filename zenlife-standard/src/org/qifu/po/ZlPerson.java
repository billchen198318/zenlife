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
		name="zl_person", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class ZlPerson extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -6325745627922024593L;

	private String oid;
	private String id;
	private String password;
	private String name;
	private String phone;
	private String tel;
	private String mail;
	private String validFlag;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;		
	
	public ZlPerson() {
		super();
	}

	public ZlPerson(String oid, String id, String name, String phone, String tel, String mail, String validFlag) {
		super();
		this.oid = oid;
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.tel = tel;
		this.mail = mail;
		this.validFlag = validFlag;
	}

	public ZlPerson(String oid, String id, String password, String name, String phone, String tel, String mail,
			String validFlag) {
		super();
		this.oid = oid;
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.tel = tel;
		this.mail = mail;
		this.validFlag = validFlag;
	}
	
	public ZlPerson(String oid, String id, String password, String name, String phone, String tel, String mail,
			String validFlag, String cuserid, Date cdate, String uuserid, Date udate) {
		super();
		this.oid = oid;
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.tel = tel;
		this.mail = mail;
		this.validFlag = validFlag;
		this.cuserid = cuserid;
		this.cdate = cdate;
		this.uuserid = uuserid;
		this.udate = udate;
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
	
	@EntityUK(name="id")
	@Column(name="ID")		
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PHONE")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name="TEL")
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Column(name="MAIL")
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Column(name="VALID_FLAG")
	public String getValidFlag() {
		return validFlag;
	}
	
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
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
