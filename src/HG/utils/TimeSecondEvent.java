package HG.utils;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TimeSecondEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}