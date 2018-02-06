package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Model.Config;

public class Sql {

	private Connection conn = null;
	private ResultSet rs = null;

	public boolean getConnection(String address, String database, String user, String password)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.conn = DriverManager.getConnection(
					"jdbc:mysql://" + address + "/" + database + "?" + "user=" + user + "&password=" + password);
			return true;
		} catch (SQLException ex) {
			// TODO: handle exception
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	public boolean getConnection(Config conf)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.conn = DriverManager.getConnection("jdbc:mysql://" + conf.getAddress() + "/" + conf.getDatabase() + "?"
					+ "user=" + conf.getUser() + "&password=" + conf.getPassword());
			return true;
		} catch (SQLException ex) {
			// TODO: handle exception
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	public boolean testConnection(Config conf)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("jdbc:mysql://" + conf.getAddress() + "/" + conf.getDatabase() + "?" + "user="
					+ conf.getUser() + "&password=" + conf.getPassword());
			this.conn = DriverManager.getConnection("jdbc:mysql://" + conf.getAddress() + "/" + conf.getDatabase() + "?"
					+ "user=" + conf.getUser() + "&password=" + conf.getPassword());
			return true;
		} catch (SQLException ex) {
			// TODO: handle exception
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			this.Close();
		}
		return false;
	}

	public boolean testConnection(String adress, String database, String user, String password)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println(
					"jdbc:mysql://" + adress + "/" + database + "?" + "user=" + user + "&password=" + password);
			this.conn = DriverManager.getConnection(
					"jdbc:mysql://" + adress + "/" + database + "?" + "user=" + user + "&password=" + password);
			return true;
		} catch (SQLException ex) {
			// TODO: handle exception
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}

	public boolean Close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) {
			} // ignore

			rs = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			}
			return true;
		}
		return false;
	}

	public ResultSet getResult(String s1, List<Object> list) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(s1);
			if (list != null) {
				int all = list.size();
				System.out.println("list-length:" + all);
				for (int i = 0; i < all; i++) {
					Object obj = list.get(i);
					if (obj.getClass() == String.class) {
						pstmt.setString(i + 1, (String) obj);
					}
					else if (obj.getClass() == Integer.class) {
						pstmt.setInt(i + 1, (Integer) obj);
					}
					else if (obj.getClass() == Float.class) {
						pstmt.setFloat(i + 1, (Float) obj);
					}
					else if (obj.getClass() == LocalDate.class) {
						pstmt.setDate(i + 1, java.sql.Date.valueOf((LocalDate) obj));
					}
					else if (obj.getClass() == LocalTime.class) {
						pstmt.setTime(i + 1, java.sql.Time.valueOf((LocalTime) obj));
					}
				}
			}
			System.out.println(pstmt.toString());
			if (pstmt.execute()) {
				rs = pstmt.getResultSet();
				return rs;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getResultEX(String s1, List<Object> list, List<Object> list1) {
		try {
			if (!list.isEmpty()&&!list1.isEmpty()) {
				int all = list.size();
				for (int i = 0; i < all; i++) {
					String col_name = (String) list.get(i);
					if (i < all - 1) {
						s1 += " " + col_name + "=? and ";
					} else {
						s1 += " " + col_name + "=?";
					}
				}
				System.out.println(s1);
				PreparedStatement pstmt = conn.prepareStatement(s1);
				System.out.println("list-length:" + all);
				for (int i = 0; i < all; i++) {
					Object obj1 = list1.get(i);
					if (obj1.getClass() == String.class) {
						pstmt.setString(i + 1, (String) obj1);
					}
					else if (obj1.getClass() == Integer.class) {
						pstmt.setInt(i + 1, (Integer) obj1);
					}
					else if (obj1.getClass() == Float.class) {
						pstmt.setFloat(i + 1, (Float) obj1);
					}
					else if (obj1.getClass() == LocalDate.class) {
						pstmt.setDate(i + 1, java.sql.Date.valueOf((LocalDate) obj1));
					}
					else if (obj1.getClass() == LocalTime.class) {
						pstmt.setTime(i + 1, java.sql.Time.valueOf((LocalTime) obj1));
					}
				}
				System.out.println(pstmt.toString());
				if (pstmt.execute()) {
					rs = pstmt.getResultSet();
					return rs;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public boolean IDU(String s1, List<Object> list) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(s1);
			int all = list.size();
			System.out.println("list-length:" + all);
			for (int i = 0; i < all; i++) {
				Object obj = list.get(i);
				if (obj.getClass() == String.class) {
					pstmt.setString(i + 1, (String) obj);
				}
				else if (obj.getClass() == Integer.class) {
					pstmt.setInt(i + 1, (Integer) obj);
				}
				else if (obj.getClass() == Float.class) {
					pstmt.setFloat(i + 1, (Float) obj);
				}
				else if (obj.getClass() == LocalDate.class) {
					pstmt.setDate(i + 1, java.sql.Date.valueOf((LocalDate) obj));
				}
				else if (obj.getClass() == LocalTime.class) {
					pstmt.setTime(i + 1, java.sql.Time.valueOf((LocalTime) obj));
				}
			}
			System.out.println(pstmt.toString());
			pstmt.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			Sql test = new Sql();
			// System.out.println(testTry(test));
			System.out.println(test.getConnection("192.168.88.201", "test", "test", "123456"));
			List<Object> list = new ArrayList<Object>();
			list.add("test");
			ResultSet rs = test.getResult("SELECT user,password FROM user WHERE user= ?", list);
			rs.next();
			System.out.println(rs.getString(2));
			listAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testINSERT(Sql test) {
		for (int i = 0; i < 5; i++) {
			// Hardware h = new Hardware(i, "" + i);
			try {
				if (test.getConnection("192.168.88.228", "test", "test", "123456")) {
					// test.Insert(h);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean testTry(Sql test) {
		try {
			return test.getConnection("192.168.88.201", "test", "test", "123456");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("off:" + test.Close());
		}
		return false;
	}

	public static void listAll() {
		Sql sql = new Sql();
		try {
			sql.getConnection("192.168.88.201", "test", "test", "123456");
			ResultSet rs = null;
			rs = sql.getResult("SELECT * FROM user", null);
			phase(rs);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sql.Close();
		}
	}

	public static void phase(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			System.out.println(col);
			while (rs.next()) {
				for (int i = 1; i < col + 1; i++) {
					System.out.println(rsmd.getColumnName(i) + ":" + rs.getObject(i));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
