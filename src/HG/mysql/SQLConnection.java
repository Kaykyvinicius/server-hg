package HG.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import HG.Main;

public class SQLConnection {

	public static Connection conn;

	public static void connectSQL() {
		if (Main.MySQL == true) {
			ConsoleCommandSender s = Bukkit.getServer().getConsoleSender();
			try {
				s.sendMessage("§e------------------------------");
				s.sendMessage("§e       Conectando ao SQL!");
				conn = DriverManager.getConnection(
						"jdbc:mysql://" + Main.MySQL_IP + ":" + Main.MySQL_Porta + "/" + Main.MySQL_Database,
						Main.MySQL_User, Main.MySQL_Password);
				PreparedStatement ps = conn.prepareStatement(
						"CREATE TABLE IF NOT EXISTS Status(UUID varchar(255), Wins varchar(255), Kills varchar(255), Deaths varchar(255), PRIMARY KEY (UUID))");
				ps.executeUpdate();
				ps.close();
				PreparedStatement ps1 = conn.prepareStatement(
						"CREATE TABLE IF NOT EXISTS Server(Server varchar(255), Status varchar(255), PRIMARY KEY (Server))");
				ps1.executeUpdate();
				ps1.close();
				s.sendMessage("§a Conexao concluida com exito!");
				s.sendMessage("§a------------------------------");
			} catch (SQLException e) {
				s.sendMessage("§c" + e.getMessage());
				s.sendMessage("§c-------------------------------");
			}
		}
	}
}