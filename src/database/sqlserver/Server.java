package database.sqlserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private String url = "jdbc:mysql://localhost:3306/";
	private String user = "root";
	private String password = "";
	private Connection con;
	private String query;
	private String listIdentifier = "{CALL printList()}";
	private String searchIdentifier = "{CALL searchMenu(?)}";
	private String historyTrackIdentifier = "{CALL trackManagementHistory(?)}";
	private String historyUpdateIdentifier = "{CALL updateManagementHistory(?,?,?,?,?)}";
	private String historyAddIdentifier = "{CALL addManagementHistory(?,?,?,?,?)}";

	public Server(String database) {
		url += database;
	}

	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<List<String>> getList() throws SQLException {
		List<List<String>> ans = new ArrayList<List<String>>();

		query = listIdentifier;
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			List<String> temp = new ArrayList<String>();
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				temp.add(resultSet.getString(i));
			}
			ans.add(temp);
		}
		return ans;
	}

	public List<String> getMenu(String searched) throws SQLException {
		List<String> ans = new ArrayList<String>();
		try {
			query = searchIdentifier;
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, searched);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					ans.add(resultSet.getString(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public List<String> trackManagementHistory(String searched) throws SQLException {
		List<String> ans = new ArrayList<String>();
		try {
			query = historyTrackIdentifier;
			PreparedStatement statement = con.prepareStatement(query);

			// convert string to SQL date format.
			try {
				Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(searched).getTime());

				statement.setDate(1, date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					ans.add(resultSet.getString(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public int updateManagementHistory(Date date, double calories, double protein, double fat, double carbs)
			throws SQLException {
		int flag = 0;
		try {
			query = historyUpdateIdentifier;
			PreparedStatement statement = con.prepareStatement(query);
			// convert string to SQL date format.
			// java.sql.Date date = new java.sql.Date(new
			// SimpleDateFormat("yyyy-MM-dd").parse(searched).getTime());
			statement.setDate(1, date);
			statement.setDouble(2, calories);
			statement.setDouble(3, protein);
			statement.setDouble(4, fat);
			statement.setDouble(5, carbs);
			flag = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int addManagementHistory(Date date, double calories, double protein, double fat, double carbs)
			throws SQLException {
		int flag = 0;
		try {
			query = historyAddIdentifier;
			PreparedStatement statement = con.prepareStatement(query);
			// convert string to SQL date format.
			// java.sql.Date date = new java.sql.Date(new
			// SimpleDateFormat("yyyy-MM-dd").parse(searched).getTime());
			statement.setDate(1, date);
			statement.setDouble(2, calories);
			statement.setDouble(3, protein);
			statement.setDouble(4, fat);
			statement.setDouble(5, carbs);
			flag = statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void main(String[] args) throws SQLException {
		Server server = new Server("tables");
		server.connect();
		// print server track history.
		// call updateManagementHistory today.
		Date today = null;
		try {
			today = new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-24").getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(server.addManagementHistory(today, 1100.0, 800.0, 30.0, 780.0));
	}
}
