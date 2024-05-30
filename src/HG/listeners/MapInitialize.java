package HG.listeners;

import javax.swing.ImageIcon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import HG.Main;
import HG.manager.GameManager;
import HG.manager.PlayerManager;
import HG.utils.Status;

public class MapInitialize implements Listener {
	@EventHandler
	public void MapInitializeEvent(MapInitializeEvent e) {
		MapView m = e.getMap();
		for (MapRenderer r : m.getRenderers()) {
			m.removeRenderer(r);
		}
		if (GameManager.status == Status.Vitória) {
			m.addRenderer(new MapRenderer() {
				public void render(MapView view, MapCanvas canvas, Player p) {
					for (Player gamer : PlayerManager.getGame()) {
						int i = 0;
						int name = gamer.getName().length();
						if (name == 16) {
							i = 22;
						}
						if (name == 15) {
							i = 25;
						}
						if (name == 14) {
							i = 28;
						}
						if (name == 13) {
							i = 30;
						}
						if (name == 12) {
							i = 33;
						}
						if (name == 11) {
							i = 36;
						}
						if (name == 10) {
							i = 38;
						}
						if (name == 9) {
							i = 41;
						}
						if (name == 8) {
							i = 44;
						}
						if (name == 7) {
							i = 46;
						}
						if (name == 6) {
							i = 49;
						}
						if (name == 5) {
							i = 52;
						}
						if (name == 4) {
							i = 54;
						}
						if (name == 3) {
							i = 57;
						}
						if (name == 2) {
							i = 60;
						}
						if (name == 1) {
							i = 62;
						}
						canvas.drawText(42, 6, MinecraftFont.Font, "PARABENS!\n");
						canvas.drawText(12, 15, MinecraftFont.Font, "\nPor ganhar o torneio!\n");
						canvas.drawText(i, 15, MinecraftFont.Font, "\n \n" + gamer.getName());
						canvas.drawImage(15, 45, new ImageIcon(Main.instance.getDataFolder().getPath() + "/Cake.png").getImage());
						break;
					}
				}
			});
		}
	}
}