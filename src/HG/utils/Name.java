package HG.utils;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.inventory.ItemStack;

public class Name {

	private static HashMap<String, String> NAMES = new HashMap<String, String>();

	public Name() {
		NAMES.put("AIR", "Hands");
		NAMES.put("WOOD_SWORD", "Wood Sword");
		NAMES.put("WOOD_AXE", "Wood Axe");
		NAMES.put("STONE_SWORD", "Stone Sword");
		NAMES.put("STONE_AXE", "Stone Axe");
		NAMES.put("IRON_SWORD", "Iron Sword");
		NAMES.put("IRON_AXE", "Iron Axe");
		NAMES.put("GOLD_SWORD", "Gold Sword");
		NAMES.put("GOLD_AXE", "Gold Axe");
		NAMES.put("DIAMOND_SWORD", "Diamond Sword");
		NAMES.put("DIAMOND_AXE", "Diamond Axe");
		NAMES.put("BOWL", "Bowl");
		NAMES.put("MUSHROOM_SOUP", "Soup");
		NAMES.put("ARROW", "Arrow");
		NAMES.put("RED_ROSE", "Red Rose");
		NAMES.put("YELLOW_FLOWER", "Yellow Flower");
	}

	@SuppressWarnings("deprecation")
	public static String getItemName(ItemStack item) {
		if (item == null)
			item = new ItemStack(0);
		if (NAMES.containsKey(item.getType().name()))
			return (String) NAMES.get(item.getType().name());
		return getName(item.getType().name());
	}

	public static String getName(String string) {
		if (NAMES.containsValue(string))
			return (String) NAMES.get(string);
		return toReadable(string);
	}

	public static String toReadable(String string) {
		String[] names = string.split("_");
		for (int i = 0; i < names.length; i++) {
			names[i] = (names[i].substring(0, 1) + names[i].substring(1)
					.toLowerCase());
		}
		return StringUtils.join(names, " ");
	}
}