package me.gorgeousone.tangledmaze.generation.building;

import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class BlockResetter extends BukkitRunnable {
	
	private final Random random = new Random();
	
	private final ActionListener callback;
	private final int blocksPerTick;
	private final Iterator<BlockState> blockIter;
	
	public BlockResetter(Set<BlockState> blocks, int blocksPerTick, ActionListener callback) {
		this.callback = callback;
		this.blocksPerTick = blocksPerTick;
		this.blockIter = blocks.iterator();
	}
	
	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		int placedBlocks = 0;
		
		while (blockIter.hasNext()) {
			blockIter.next().update(true, false);
			++placedBlocks;
			
			if (blockLimitReached(placedBlocks, blocksPerTick, startTime)) {
				return;
			}
		}
		if (callback != null) {
			callback.actionPerformed(null);
			this.cancel();
		}
	}
	
	boolean blockLimitReached(int placedBlocks, int bpt, long startTime) {
		if (bpt > -1) {
			if (placedBlocks >= bpt) {
				return true;
			}
		}
		return System.currentTimeMillis() - startTime >= 40;
	}
}