package user.managment.db;

public class DBConnectionProperty {

	private String driver;
	private String user;
	private String password;
	private String url;

	public DBConnectionProperty() {
	}

	public DBConnectionProperty(String driver, String user, String password, String url) {
		super();
		this.driver = driver;
		this.user = user;
		this.password = password;
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String db) {
		this.url = db;
	}
}
