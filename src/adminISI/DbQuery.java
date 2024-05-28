package adminISI;

import java.sql.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DbQuery {

	private String user;
	private String password;
	private String url;

	public DbQuery(String user, String password, String url) {
		this.user = user;
		this.password = password;
		this.url = url;
	}

	public void createTablesIfNeeded() {
		try (Connection con = DriverManager.getConnection(url, user, password)) {
			if (!tablesExist(con)) {
				createTables(con);
			} else {
				System.out.println("Tables already exist.");
			}
		} catch (SQLException e) {
			System.out.println("Error creating tables: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Method to check if tables exist
	private boolean tablesExist(Connection con) throws SQLException {
		DatabaseMetaData metaData = con.getMetaData();
		ResultSet resultSet = metaData.getTables(null, null, "tb_enseignant", null);
		boolean enseignantTableExists = resultSet.next();
		resultSet.close();

		resultSet = metaData.getTables(null, null, "tb_cours", null);
		boolean coursTableExists = resultSet.next();
		resultSet.close();

		resultSet = metaData.getTables(null, null, "authentication", null);
		boolean authTableExists = resultSet.next();
		resultSet.close();

		return enseignantTableExists && coursTableExists && authTableExists;
	}

	public boolean signUpAcc(String name, String email, String password) {

		String query = "INSERT INTO authentication (name, email, password) VALUES (?, ?, ?)";
		try (Connection con = DriverManager.getConnection(url, user, this.password);
				PreparedStatement statement = con.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setString(2, email);
			statement.setString(3, password);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("User data inserted successfully.");
				return true;
			} else {
				System.out.println("Failed to insert user data.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error inserting user data: " + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	private void createTables(Connection con) throws SQLException {
		try (Statement statement = con.createStatement()) {
			// Create table tb_enseignant
			String createEnseignantTableSQL = "CREATE TABLE tb_enseignant (" + "matricule VARCHAR(20) PRIMARY KEY, "
					+ "nom VARCHAR(150) NOT NULL, " + "contact VARCHAR(50) NOT NULL)";
			statement.executeUpdate(createEnseignantTableSQL);

			// Create table tb_cours
			String createCoursTableSQL = "CREATE TABLE tb_cours (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "classe VARCHAR(30) NOT NULL, " + "matiere VARCHAR(80) NOT NULL, " + "num_jour SMALLINT, "
					+ "Jour VARCHAR(20) NOT NULL, " + "heure VARCHAR(20) NOT NULL, "
					+ "matricule_ens VARCHAR(20) NOT NULL, "
					+ "FOREIGN KEY (matricule_ens) REFERENCES tb_enseignant(matricule))";
			statement.executeUpdate(createCoursTableSQL);

			// Create table for authentication
			String createAuthTableSQL = "CREATE TABLE authentication (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
					+ "name VARCHAR(150) NOT NULL, " + "email VARCHAR(100) UNIQUE NOT NULL, "
					+ "password VARCHAR(100) NOT NULL)";
			statement.executeUpdate(createAuthTableSQL);

			System.out.println("Tables created successfully.");
		} catch (SQLException e) {
			System.out.println("Error creating tables: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String select(String query) throws SQLException {
		boolean dontAdd2Points = Objects.equals(query, "select classe from tb_cours") ? true : false;
		String data = "";
		Connection con = null;
		Statement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					data += resultSet.getString(i);
					if (!dontAdd2Points)
						data += ":";
				}
				data += "\n";
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		}
		// System.out.println(data);
		return data;
	}

	// auth
	public boolean authenticateUser(String email, String password) {
		String query = "SELECT COUNT(*) FROM authentication WHERE email = ? AND password = ?";
		try (Connection con = DriverManager.getConnection(url, user, this.password);
				PreparedStatement statement = con.prepareStatement(query)) {
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count == 1; // If count is 1, user exists and password matches
			}
		} catch (SQLException e) {
			System.out.println("Error authenticating user: " + e.getMessage());
			e.printStackTrace();
		}
		return false; // Default return false if authentication fails
	}

	// liste roulantante
	public Set<String> getClassesNames() throws SQLException {
		String query = "select classe from tb_cours";
		Set<String> uniqueValues = new HashSet<>();

		// Split the input string by newline characters
		String[] values = select(query).split("\n");

		// Add each value to the set
		for (String value : values) {
			uniqueValues.add(value);
		}

		return uniqueValues;
	}

	// Method to search for an instructor by name
	public String searchInstructorByName(String name) throws SQLException {
		String query = "SELECT matricule, nom, contact FROM tb_enseignant WHERE nom like '%" + name + "%'";
		return select(query);
	}

	// Method to update instructor information by matricule
	public void updateInstructor(String matricule, String nom, String contact) throws SQLException {
		String query = "UPDATE tb_enseignant SET nom = '" + nom + "', contact = '" + contact + "' WHERE matricule = '"
				+ matricule + "'";
		executeUpdate(query, "Instructor updated successfully.", "Failed to update instructor.");
	}

	// Helper method for executing update queries (insert, update, delete)
	private void executeUpdate(String query, String successMessage, String failureMessage) throws SQLException {
		Connection con = null;
		Statement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			statement = con.createStatement();
			int rowsAffected = statement.executeUpdate(query);
			if (rowsAffected > 0) {
				System.out.println(successMessage);
			} else {
				System.out.println(failureMessage);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	// Method to insert a new course session into the database
	public boolean insertCourseSession(String classe, String matiere, int num_jour, String jour, String heure,
			String matricule_ens) {
		String query = "INSERT INTO tb_cours (classe, matiere, num_jour, Jour, heure, matricule_ens) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement statement = con.prepareStatement(query)) {
			statement.setString(1, classe);
			statement.setString(2, matiere);
			statement.setInt(3, num_jour);
			statement.setString(4, jour);
			statement.setString(5, heure);
			statement.setString(6, matricule_ens);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("A new course session was inserted successfully.");
				return true;
			} else {
				System.out.println("Failed to insert a new course session.");
				return false;
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Duplicate entry: " + e.getMessage());
			return false;
		} catch (SQLException e) {
			System.out.println("Error inserting a new course session: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	// Method to fetch course sessions for a specific class and subject
	public String searchCourseSessions(String classe, String matiere) throws SQLException {
		String query = "SELECT id,classe,matiere,jour,heure,nom,contact FROM tb_cours, tb_enseignant "
				+ "where tb_cours.matricule_ens = tb_enseignant.matricule " + "AND classe = '" + classe
				+ "' AND matiere = '" + matiere + "'";
		return select(query);
	}

	// Method to delete a course session by ID
	public boolean deleteCourseSession(int id) {
		String query = "DELETE FROM tb_cours WHERE id = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement statement = con.prepareStatement(query)) {
			statement.setInt(1, id);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Course session deleted successfully.");
				return true;
			} else {
				System.out.println("Failed to delete course session.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error deleting course session: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	// Method to fetch timetable for a specific class
	public String fetchTimetableForClass(String classe) throws SQLException {
		String query = "SELECT id,classe,matiere,jour,heure,nom,contact FROM tb_cours, tb_enseignant "
				+ "WHERE tb_cours.matricule_ens = tb_enseignant.matricule AND classe = '" + classe + "'";
		return select(query);
	}

	// Method to insert a new instructor into the database
	public void insertInstructor(String matricule, String nom, String contact) {
		String query = "INSERT INTO tb_enseignant (matricule, nom, contact) VALUES (?, ?, ?)";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement statement = con.prepareStatement(query)) {
			statement.setString(1, matricule);
			statement.setString(2, nom);
			statement.setString(3, contact);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Instructor inserted successfully.");
			} else {
				System.out.println("Failed to insert instructor.");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Duplicate entry: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error inserting instructor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Method to delete an instructor by matricule
	public boolean deleteInstructor(String matricule) {
		// lazim nafs5o cours associted lil prof , forgien key
		String deleteCourseSessionsQuery = "DELETE FROM tb_cours WHERE matricule_ens = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement deleteCourseSessionsStatement = con.prepareStatement(deleteCourseSessionsQuery)) {
			deleteCourseSessionsStatement.setString(1, matricule);
			int courseSessionsDeleted = deleteCourseSessionsStatement.executeUpdate();
			System.out.println(
					"Deleted " + courseSessionsDeleted + " course sessions associated with instructor " + matricule);
		} catch (SQLException e) {
			System.out.println("Error deleting course sessions associated with instructor: " + e.getMessage());
			e.printStackTrace();
		}

		// After deleting associated course sessions, proceed with deleting the
		// instructor
		String deleteInstructorQuery = "DELETE FROM tb_enseignant WHERE matricule = ?";
		try (Connection con = DriverManager.getConnection(url, user, password);
				PreparedStatement deleteInstructorStatement = con.prepareStatement(deleteInstructorQuery)) {
			deleteInstructorStatement.setString(1, matricule);
			int instructorDeleted = deleteInstructorStatement.executeUpdate();
			if (instructorDeleted > 0) {
				System.out.println("Instructor deleted successfully.");
				return true;
			} else {
				System.out.println("Failed to delete instructor: Instructor not found.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error deleting instructor: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

}
