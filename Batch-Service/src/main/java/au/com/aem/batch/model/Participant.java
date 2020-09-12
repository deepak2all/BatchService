package au.com.aem.batch.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Participant {

	@Id
	private Integer id;
	
	private String participantId;
	
	private String participantName;
	
	private String participantEmail;
	
	private String participantPhone;
	
	private String status;
	
	private Date time; // Needed only if you want to append timestamp column to the table data
	
	public Participant() {}

	public Participant(Integer id, String participantId, String participantName, String participantEmail,
			String participantPhone, String status, Date time) {
		this.id = id;
		this.participantId = participantId;
		this.participantName = participantName;
		this.participantEmail = participantEmail;
		this.participantPhone = participantPhone;
		this.status = status;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public String getParticipantEmail() {
		return participantEmail;
	}

	public void setParticipantEmail(String participantEmail) {
		this.participantEmail = participantEmail;
	}

	public String getParticipantPhone() {
		return participantPhone;
	}

	public void setParticipantPhone(String participantPhone) {
		this.participantPhone = participantPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Participant [id=" + id + ", participantId=" + participantId + ", participantName=" + participantName
				+ ", participantEmail=" + participantEmail + ", participantPhone=" + participantPhone + ", status="
				+ status + "]";
	}
	
}
