package me.gorgeousone.tangledmaze.util.blocktype;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

/**
 * A wrapper for block materials and their (block-) data to support copying blocks across all Minecraft versions.
 */
public abstract class BlockType {
	
	private static Boolean isLegacyServer;
	
	public abstract Material getType();
	
	public abstract BlockState updateBlock(Block block, boolean physics);
	
	public abstract void sendBlockChange(Player player, Location location);
	
	public abstract BlockType clone();
	
	/**
	 * Sets whether BlockType.of() will create a LegacyBlockType or an AquaticBlockType
	 */
	public static void configureVersion(boolean isLegacyServer) {
		BlockType.isLegacyServer = isLegacyServer;
	}
	
	public static BlockType get(Block block) {
		return isLegacyServer ? new BlockTypeLegacy(block) : new BlockTypeAquatic(block);
	}
	
	public static BlockType get(Material material) {
		return isLegacyServer ? new BlockTypeLegacy(material) : new BlockTypeAquatic(material);
	}
	
	public static BlockType get(BlockState state) {
		return isLegacyServer ? new BlockTypeLegacy(state) : new BlockTypeAquatic(state);
	}
	
	public static BlockType of(String serialized) {
		return isLegacyServer ? new BlockTypeLegacy(serialized) : new BlockTypeAquatic(serialized);
	}
}