package user.managment.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {
	
	private int status;
	private String message;
	private String link;

	public Error() {
	}
	
	public Error(int status, String message, String link) {
		this.status = status;
		this.message = message;
		this.link = link;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
