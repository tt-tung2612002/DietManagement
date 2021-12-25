package database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.sqlserver.Server;

public class DatabaseManager {
	private Server server;
	private List<List<String>> dictList;

	public DatabaseManager() throws SQLException {
		server = new Server("tables");
		server.connect();
		dictList = server.getList();
	}

	public List<List<String>> getMenu() {
		return dictList;
	}

	public List<String> getMenuList() {
		List<String> ans = new ArrayList<String>();
		for (int i = 0; i < dictList.size(); i++) {
			ans.add(dictList.get(i).get(0));
		}
		return ans;
	}

	public List<String> getHistory(String date) {
		List<String> ans = null;
		try {
			ans = server.trackManagementHistory(date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public int updateHistory(Date date, double calories, double protein, double fat, double carbs)
			throws SQLException {
		int flag = 0;
		flag = server.addManagementHistory(date, calories, protein, fat, carbs);
		return flag == 0 ? server.updateManagementHistory(date, calories, protein, fat, carbs) : flag;
	}

	public Server getServer() {
		return server;
	}

}
