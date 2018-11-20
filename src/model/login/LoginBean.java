package model.login;



public class LoginBean {
	String user;
	String pass;
	
	public LoginBean(){
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String comprobar() {
		
		return "ok";
	}
	
	
}
