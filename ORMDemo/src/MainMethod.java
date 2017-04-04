public class MainMethod {

	public static void main(String[] args) {

		try {
			Class<?> clazz = Class.forName("JDBC.Student");
			SQLHelper sql = new SQLHelper("jdbc:mysql://localhost:3306/jdbc",
					"root", "123");

			sql.createTable(clazz, "student", "id");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
