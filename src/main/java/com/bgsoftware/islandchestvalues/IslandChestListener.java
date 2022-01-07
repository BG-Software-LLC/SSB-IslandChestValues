package com.bgsoftware.islandchestvalues;

import com.bgsoftware.superiorskyblock.api.events.IslandWorthCalculatedEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandChest;
import com.bgsoftware.superiorskyblock.api.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class IslandChestListener implements Listener {

    private final JavaPlugin plugin;

    public IslandChestListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onIslandCalc(IslandWorthCalculatedEvent e) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            e.getIsland().clearBlockCounts();
            for (IslandChest islandChest : e.getIsland().getChest()) {
                for (ItemStack itemStack : islandChest.getContents()) {
                    if (itemStack != null)
                        e.getIsland().handleBlockPlace(Key.of(itemStack), itemStack.getAmount());
                }
            }
        }, 1L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onIslandChestInteract(InventoryClickEvent e) {
        Inventory inventory = e.getView().getTopInventory();
        InventoryHolder inventoryHolder = inventory == null ? null : inventory.getHolder();

        if (!(inventoryHolder instanceof IslandChest))
            return;

        Island island = ((IslandChest) inventoryHolder).getIsland();

        switch (e.getAction()) {
            case MOVE_TO_OTHER_INVENTORY:
                if (e.getClickedInventory() != inventory) {
                    island.handleBlockPlace(Key.of(e.getCurrentItem()), e.getCurrentItem().getAmount());
                } else {
                    island.handleBlockBreak(Key.of(e.getCurrentItem()), e.getCurrentItem().getAmount());
                }
                break;
            case PLACE_ALL:
                if (e.getClickedInventory() == inventory)
                    island.handleBlockPlace(Key.of(e.getCursor()), e.getCursor().getAmount());
                break;
            case PLACE_ONE:
                if (e.getClickedInventory() == inventory)
                    island.handleBlockPlace(Key.of(e.getCursor()), 1);
                break;
            case PICKUP_ALL:
                if (e.getClickedInventory() == inventory)
                    island.handleBlockBreak(Key.of(e.getCurrentItem()), e.getCurrentItem().getAmount());
                break;
            case PICKUP_HALF:
                if (e.getClickedInventory() == inventory)
                    island.handleBlockBreak(Key.of(e.getCurrentItem()), e.getCurrentItem().getAmount() / 2);
                break;
            case PICKUP_ONE:
                if (e.getClickedInventory() == inventory)
                    island.handleBlockBreak(Key.of(e.getCurrentItem()), 1);
                break;
            case HOTBAR_SWAP: {
                ItemStack itemStack = e.getView().getBottomInventory().getItem(e.getHotbarButton());
                island.handleBlockPlace(Key.of(itemStack), itemStack.getAmount());
                break;
            }
            case HOTBAR_MOVE_AND_READD: {
                ItemStack itemStack = e.getView().getBottomInventory().getItem(e.getHotbarButton());
                island.handleBlockPlace(Key.of(itemStack), itemStack.getAmount());
                island.handleBlockBreak(Key.of(e.getCurrentItem()), e.getCurrentItem().getAmount());
                break;
            }
        }

    }

}
