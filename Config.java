package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONObject;

public class Config {
	private String address;
	private String database;
	private String user;
	private String password;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
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

	public boolean exists(String path) {
		File file = new File(path+"Config/config.txt");
		return file.exists();
	}
	
	public boolean load(String path) {
		try {
			File file = new File(path+"Config/config.txt");
			FileReader in = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader buff = new BufferedReader(in);
			String data = new String();
			String line = null;
			while ((line = buff.readLine()) != null) {
				// System.out.println(line);
				data += line;
			}
			System.out.println(data);
			JSONObject json = new JSONObject(data);
			this.setAddress(json.getString("address"));
			this.setDatabase(json.getString("database"));
			this.setUser(json.getString("user"));
			this.setPassword(json.getString("password"));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public boolean generateConfig(String path) {
		try {
			JSONObject json = new JSONObject();
			json.put("address", this.getAddress());
			json.put("database", this.getDatabase());
			json.put("user", this.getUser());
			json.put("password", this.getPassword());
			File file = new File(path+"Config/config.txt");
			file.createNewFile();
			FileWriter out = new FileWriter(file);
			out.write(json.toString());
			out.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		generate();
		check();
	}

	public static void generate() {
		try {
			JSONObject json = new JSONObject();
			json.put("address", "192.168.88.228");
			json.put("database", "test");
			json.put("user", "test");
			json.put("password", "123456");
			File file = new File("Config/config.txt");
			file.createNewFile();
			FileWriter out = new FileWriter(file);
			out.write(json.toString());
			out.close();
			System.out.println("Generate");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void check() {
		File file = new File("Config/config.txt");
		// System.out.println(file.exists());
		if (file.exists()) {
			try {
				FileReader in = new FileReader(file);
				@SuppressWarnings("resource")
				BufferedReader buff = new BufferedReader(in);
				String data = new String();
				String line = null;
				while ((line = buff.readLine()) != null) {
					// System.out.println(line);
					data += line;
				}
				System.out.println(data);
				JSONObject json = new JSONObject(data);
				Config config = new Config();
				config.setAddress(json.getString("address"));
				config.setDatabase(json.getString("database"));
				config.setUser(json.getString("user"));
				config.setPassword(json.getString("password"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			System.out.println("Need Setup");
		}
	}

}
