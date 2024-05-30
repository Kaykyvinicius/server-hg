package HG.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import HG.Main;

public class SQLStatus {
	public static void setStatus(Player p) {
		if (Main.MySQL == true) {
			try {
				PreparedStatement ps = SQLConnection.conn
						.prepareStatement("SELECT * FROM Status WHERE UUID='" + p.getUniqueId() + "'");
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					PreparedStatement ps1 = SQLConnection.conn
							.prepareStatement("INSERT INTO Status VALUES('" + p.getUniqueId() + "', '0', '0', '0')");
					ps1.executeUpdate();
					ps1.close();
				}
				ps.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void addStatus(Player p, String status) {
		if (Main.MySQL == true) {
			try {
				PreparedStatement ps = SQLConnection.conn
						.prepareStatement("SELECT * FROM Status WHERE UUID='" + p.getUniqueId() + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					PreparedStatement ps2 = SQLConnection.conn.prepareStatement("UPDATE Status SET " + status + "='"
							+ Integer.toString(rs.getInt(status) + 1) + "' WHERE UUID='" + p.getUniqueId() + "'");
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
			} catch (SQLException e) {
			}
		}
	}

	public static String getStatus(Player p, String status) {
		if (Main.MySQL == true) {
			try {
				PreparedStatement ps = SQLConnection.conn
						.prepareStatement("SELECT * FROM Status WHERE UUID='" + p.getUniqueId() + "'");
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					return rs.getString(status);
				}
			} catch (SQLException e) {
			}
		}
		return null;
	}

	public static void setStatusServer(int status) {
		if (Main.MySQL == true) {
			try {
				PreparedStatement ps = SQLConnection.conn
						.prepareStatement("SELECT * FROM Server WHERE Server='" + Main.IP + "'");
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					PreparedStatement ps1 = SQLConnection.conn
							.prepareStatement("INSERT INTO Server VALUES('" + Main.IP + "', '" + status + "')");
					ps1.executeUpdate();
					ps1.close();
				} else {
					PreparedStatement ps2 = SQLConnection.conn.prepareStatement(
							"UPDATE Server SET Status='" + status + "' WHERE Server='" + Main.IP + "'");
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
			} catch (SQLException e) {
			}
		}
	}
}