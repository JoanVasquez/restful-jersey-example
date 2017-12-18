package user.managment.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

@XmlRootElement
public class User {

	private int userId;
	@NotNull
	@Pattern(regexp = "^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$", message = "Only letters and space")
	@Size(min = 2, max = 20)
	private String name;

    @NotNull(message = "It cannot be null")
    @Size(min = 5, max = 60)
    @Email(message = "{Wrong email format", regexp = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
	private String email;
    
	private byte[] pass;
	
	@NotNull
	@Size(min = 2, max = 8)
	private String tempPass;

	public User() {
	}

	public User(int userId, String name, String email, String tempPass) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.tempPass = tempPass;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPass() {
		return pass;
	}

	public void setPass(byte[] pass) {
		this.pass = pass;
	}

	public String getTempPass() {
		return tempPass;
	}

	public void setTempPass(String tempPass) {
		this.tempPass = tempPass;
	}

}
