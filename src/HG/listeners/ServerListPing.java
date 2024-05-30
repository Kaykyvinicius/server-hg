package HG.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.ScoreBoard;
import HG.utils.Status;

public class ServerListPing implements Listener {
	@EventHandler
	public void ServerListPinge(ServerListPingEvent e) {
		if (GameManager.status == Status.PréJogo) {
			e.setMotd("§c§nWixedHG§r §4§n2.0§r §7§l§nServidor§r §7§l§nde§r §6§l§nTestes§r");// + ScoreBoard.time(GameManager.pre));
		} else if (GameManager.status == Status.Invencibilidade) {
			e.setMotd("§c§nWixedHG§r\n§cInivencibilidade acaba em " + ScoreBoard.time(GameManager.inven));
			e.setMaxPlayers(PlayerManager.getGame().length);
		} else if (GameManager.status == Status.Jogo) {
			e.setMotd("§c§nWixedHG§r\n§cTorneio em progressso" + ScoreBoard.time(GameManager.game));
			e.setMaxPlayers(PlayerManager.getGame().length);
		} else if (GameManager.status == Status.Vitória) {
			e.setMotd("§c§nWixedHG§r\n§4Servidor Reiniciando");
		}
	}
}