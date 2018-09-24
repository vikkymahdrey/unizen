package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_lora_frm_flag database table.
 * 
 */
@Entity
@Table(name="tbl_lora_frm_flag")
@NamedQuery(name="TblLoraFrmFlag.findAll", query="SELECT t FROM TblLoraFrmFlag t")
public class TblLoraFrmFlag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_dt")
	private Date createdDt;

	private String deviceEUI;

	private String devId;

	private String flag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_dt")
	private Date updatedDt;

	public TblLoraFrmFlag() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getDeviceEUI() {
		return this.deviceEUI;
	}

	public void setDeviceEUI(String deviceEUI) {
		this.deviceEUI = deviceEUI;
	}

	public String getDevId() {
		return this.devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getUpdatedDt() {
		return this.updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

}