
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class SQLHelper {
	Connection connection = null;
	static final Map<Class<?>, String> m1 = new HashMap<Class<?>, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(String.class, "varchar(45)");
			put(float.class, "float");
			put(double.class, "double");
			put(int.class, "int");
			put(char.class, "char");
			put(byte.class, "binary");
			put(short.class, "smallint");
			put(long.class, "bigint");
			put(boolean.class, "tinyint");
		}
	};

	public SQLHelper() {

	}

	public SQLHelper(String Url, String userName, String passWord) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(Url, userName, passWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createTable(Class<?> clazz, String tableName,
			String primaryKeyName) {
		String sql = "create table " + tableName + "(";
		Field[] field = clazz.getDeclaredFields();
		PreparedStatement ps = null;
		int length = field.length;
		for (int i = 0; i < length; i++) {
			if (i != length - 1) {
				sql += field[i].getName() + " " + m1.get(field[i].getType())
						+ " " + "not null" + "," + "\r\n";
			} else {
				sql += field[i].getName() + " " + m1.get(field[i].getType())
						+ " " + "not null," + "\r\n" + "primary key " + "("
						+ primaryKeyName + ")" + ");";
			}
		}
		try {
			ps = connection.prepareStatement(sql);
			try {
				ps.execute();
			} catch (MySQLSyntaxErrorException e) {
				System.out.println("the table named " + tableName + " exists");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert() {

	}
}
