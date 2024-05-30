package HG.manager;

import org.bukkit.Material;

public enum Kits {
	Achilles("Achilles", Material.WOOD_SWORD, 0, 1),
	Blink("Blink", Material.NETHER_STAR, 0, 1),
	Camel("Camel", Material.SAND, 0, 1),
	Cannibal("Cannibal", Material.MONSTER_EGG, 98, 1),
	Castaway("Castaway", Material.DISPENSER, 0, 1),
	Cookiemonster("Cookiemonster", Material.COOKIE, 0, 1),
	Cultivator("Cultivator", Material.SEEDS, 0, 1),
	Demoman("Demoman", Material.STONE_PLATE, 0, 1),
	Endermage("Endermage", Material.PORTAL, 0, 1),
	Fireman("Fireman", Material.LAVA_BUCKET, 0, 1),
	Fisherman("Fisherman", Material.FISHING_ROD, 0, 1),
	Forger("Forger", Material.COAL, 0, 1),
	Gambler("Gambler", Material.STONE_BUTTON, 0, 1),
	Gladiator("Gladiator", Material.IRON_FENCE, 0, 1),
	Grandpa("Grandpa", Material.STICK, 0, 1),
	Grappler("Grappler", Material.LEASH, 0, 1),
	Hermit("Hermit", Material.MUSHROOM_SOUP, 0, 1),
	Jackhammer("Jackhammer", Material.STONE_AXE, 0, 1),
	//Jellyfish("Jellyfish", Material.INK_SACK, 0, 1),
	Kangaroo("Kangaroo", Material.FIREWORK, 0, 1),
	Kaya("Kaya", Material.GRASS, 0, 1),
	Launcher("Launcher", Material.SPONGE, 0, 1),
	Lumberjack("Lumberjack", Material.WOOD_AXE, 0, 1),
	Madman("Madman", Material.POTION, 8264, 1),
	Magma("Magma", Material.MAGMA_CREAM, 0, 1),
	Miner("Miner", Material.STONE_PICKAXE, 0, 1),
	Ninja("Ninja", Material.WOOL, 15, 1),
	Poseidon("Poseidon", Material.WATER_BUCKET, 0, 1),
	Reaper("Reaper", Material.WOOD_HOE, 0, 1),
	Redstoner("Redstoner", Material.REDSTONE, 0, 1),
	Snail("Snail", Material.POTION, 10, 1),
	Specialist("Specialist", Material.BOOK, 0, 1),
	Stomper("Stomper", Material.IRON_BOOTS, 0, 1),
	Surprise("Surprise", Material.CAKE, 0, 1),
	Switcher("Switcher", Material.SNOW_BALL, 0, 1),
	Tank("Tank", Material.TNT, 0, 1),
	Thor("Thor", Material.WOOD_AXE, 0, 1),
	Titan("Titan", Material.BEDROCK, 0, 1),
	Turtle("Turtle", Material.DIAMOND_CHESTPLATE, 0, 1),
	Urgal("Urgal", Material.POTION, 8201, 1),
	Viking("Viking", Material.IRON_AXE, 0, 1),
	Viper("Viper", Material.POTION, 16388, 1);
	String name;
	Material material;
	int data;
	int quantidade;

	Kits(String Name, Material material, int data, int quantidade) {
		this.name = Name;
		this.material = material;
		this.data = data;
		this.quantidade = quantidade;
	}

	public String getName() {
		return this.name;
	}

	public Material getMaterial() {
		return this.material;
	}

	public int data() {
		return this.data;
	}

	public int quantidade() {
		return this.quantidade;
	}
}