package HG.manager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import HG.Main;
import HG.utils.ScoreBoard;
import HG.utils.Status;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class VersionManager {
	public static int VERSION = 47;

	public static void sendTab(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		int slot = 0;
		int tempo = 0;
		if (GameManager.status == Status.PréJogo) {
			tempo = GameManager.pre;
			slot = Bukkit.getMaxPlayers();
		} else if (GameManager.status == Status.Invencibilidade) {
			tempo = GameManager.inven;
			slot = PlayerManager.getGame().length;
		} else if (GameManager.status == Status.Jogo) {
			tempo = GameManager.game;
			slot = PlayerManager.getGame().length;
		}
		PlayerConnection connect = ((CraftPlayer) p).getHandle().playerConnection;
		IChatBaseComponent top = ChatSerializer.a("{text: '" + p.getName() + " §6Ping: §f"
				+ ((CraftPlayer) p).getHandle().ping + " §6Kit: §f" + KitManager.playerKit(p) + "'}");
		IChatBaseComponent bottom = ChatSerializer.a("{text: '§6Tempo: §f" + ScoreBoard.time(tempo) + " §6IP: §f"
				+ Main.IP + " §c" + PlayerManager.getGame().length + "/" + slot + "', 'underline': 'true'}");
		connect.sendPacket(new ProtocolInjector.PacketTabHeader(top, bottom));
	}

	public static void sendActionBar(Player p, String msg) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public static void sendTitle(Player p, String title) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(
				ProtocolInjector.PacketTitle.Action.TITLE, ChatSerializer.a("{\"text\": \"\"}").a(title)));
	}

	public static void sendSubTitle(Player p, String subtitle) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(
				ProtocolInjector.PacketTitle.Action.SUBTITLE, ChatSerializer.a("{\"text\": \"\"}").a(subtitle)));
	}

	public static void sendTimings(Player p, int fadeIn, int stay, int fadeOut) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		try {
			Object handle = getHandle(p);
			Object connection = getField(handle.getClass(), "playerConnection").get(handle);
			Object packet = ProtocolInjector.PacketTitle.class
					.getConstructor(new Class[] { ProtocolInjector.PacketTitle.Action.class, Integer.TYPE, Integer.TYPE,
							Integer.TYPE })
					.newInstance(new Object[] { ProtocolInjector.PacketTitle.Action.TIMES, Integer.valueOf(fadeIn),
							Integer.valueOf(stay), Integer.valueOf(fadeOut) });
			getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, new Object[] { packet });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length) {
			return false;
		}
		for (int i = 0; i < l1.length; i++) {
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		}
		return equal;
	}

	private static Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
		Method[] arrayOfMethod;
		int j = (arrayOfMethod = clazz.getMethods()).length;
		for (int i = 0; i < j; i++) {
			Method m = arrayOfMethod[i];
			if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes())))) {
				m.setAccessible(true);
				return m;
			}
		}
		return null;
	}

	private static Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void reset(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.RESET));
	}

	public static void clear(Player p) {
		if (((CraftPlayer) p).getHandle().playerConnection.networkManager.getVersion() < VERSION) {
			return;
		}
		((CraftPlayer) p).getHandle().playerConnection
				.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.CLEAR));
	}
}