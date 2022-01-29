package me.drigster.foxyprotection.events;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.*;

public class PrivateEvents implements Listener {

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (chunk.getZ() == 0){
            if (chunk.getX() == 0){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockBurn(BlockBurnEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (chunk.getZ() == 0){
            if (chunk.getX() == 0){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockExplode(BlockExplodeEvent e){
        List<Block> blocks = e.blockList();
        List<Block> remove = new ArrayList<>();
        for (int i=0; i<blocks.size(); i++)
        {
            Block block = blocks.get(i);
            Chunk chunk = block.getChunk();
            if (chunk.getZ() == 0){
                if (chunk.getX() == 0){
                    remove.add(blocks.get(i));
                }
            }
        }
        blocks.removeAll(remove);
    }

    @EventHandler
    private void onBlockIngnite(BlockIgniteEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (chunk.getZ() == 0){
            if (chunk.getX() == 0){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent e){
        Block block = e.getBlock();
        Chunk chunk = block.getChunk();
        if (chunk.getZ() == 0){
            if (chunk.getX() == 0){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onEntityExplode(EntityExplodeEvent e){
        List<Block> blocks = e.blockList();
        List<Block> remove = new ArrayList<>();
        for (int i=0; i<blocks.size(); i++)
        {
            Block block = blocks.get(i);
            Chunk chunk = block.getChunk();
            if (chunk.getZ() == 0){
                if (chunk.getX() == 0){
                    remove.add(blocks.get(i));
                }
            }
        }
        blocks.removeAll(remove);
    }
}
