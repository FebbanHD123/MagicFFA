package de.febanhd.ffa.gui;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SimpleGui implements Listener {

    static boolean isRegistered;
    static ArrayList<SimpleGui> guiList;

    private HashMap<Integer, ItemStack> slotItems;
    private HashMap<Integer, Integer> slotExecuteIDs;
    private Inventory gui;
    private String guiTitle;
    private int guiSize;
    private boolean cancelClick, closeOnClick;
    private InventoryType guiTyp;
    private long createdAt;
    private List<Player> players;
    private Player opener;

    private JavaPlugin plugin;

    public SimpleGui(JavaPlugin plugin) {
        this.plugin = plugin;
        this.slotItems = new HashMap();
        this.slotExecuteIDs = new HashMap();
        this.createdAt = System.currentTimeMillis();
        this.players = Lists.newArrayList();

        if(!isRegistered) {
            isRegistered = true;
            Bukkit.getPluginManager().registerEvents(this, plugin);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
                for(SimpleGui gui : guiList) {
                    gui.onUpdate();
                }
            }, 0, 1);
        }
        cancelClick = true;
        this.closeOnClick = false;
    }

    static {
        isRegistered = false;
        guiList = new ArrayList<>();
    }

    public abstract void initGui();
    public abstract void onClick(int executeID, InventoryClickEvent inventoryClickEvent);

    public ItemStack addItem(int slot, ItemStack stack, int executeId) {
        this.slotItems.put(slot, stack);
        this.slotExecuteIDs.put(slot, executeId);
        return stack;
    }

    public void removeItem(int slot) {
        this.slotItems.remove(slot);
        this.slotExecuteIDs.remove(slot);
    }

    public void setTitle(String newTitle) {
        this.guiTitle = newTitle;
        if(gui != null) {
            ItemStack[] contents = gui.getContents();
            gui = Bukkit.createInventory(null, guiSize, guiTitle);
            this.gui.setContents(contents);
        }
    }

    public void setGuiRows(int guiRows) {
        this.guiSize = (guiRows * 9);
    }

    public void setCancelClick(boolean cancelClick) {
        this.cancelClick = cancelClick;
    }

    public void setGuiTyp(InventoryType guiTyp) {
        this.guiTyp = guiTyp;
    }

    public void setCloseOnClick(boolean closeOnClick) {
        this.closeOnClick = closeOnClick;
    }

    private Inventory build() {
        this.initGui();
        if(this.guiTyp == null) {
            this.guiTyp = InventoryType.CHEST;
        }
        if(guiTitle == null) {
            throw new NullPointerException("GuiTitle is Null");
        }
        Inventory inventory;
        switch(guiTyp) {
            case CHEST:
                inventory = Bukkit.createInventory(null, guiSize, guiTitle);
                break;
            default:
                inventory = Bukkit.createInventory(null, guiTyp, guiTitle);
                break;
        }
        for(int slot : this.slotItems.keySet()) {
            if(slot < this.guiSize) {
                inventory.setItem(slot, this.slotItems.get(slot));
            }
        }
        this.gui = inventory;
        guiList.add(this);
        return inventory;
    }

    public void open(Player player) {
        this.opener = player;
        player.openInventory(this.build());
    }

    public void onUnfilteredClick(InventoryClickEvent event) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        Inventory openInventory = event.getWhoClicked().getOpenInventory().getTopInventory();
        String openInvTitle = openInventory.getTitle();
        ItemStack stack = event.getCurrentItem();
        if(stack == null) return;
        for(SimpleGui gui : Lists.newArrayList(this.guiList)) {
            if(gui.getGuiTitle().equals(openInvTitle) && event.getWhoClicked().getUniqueId().equals(gui.getOpener().getUniqueId())) {
                if(gui.isCancelClick()) {
                    event.setCancelled(true);
                }
                gui.onUnfilteredClick(event);
                if(gui.getSlotExecuteIDs().containsKey(event.getSlot()) && gui.getSlotItems().get(event.getSlot()).equals(stack)) {
                    int executeID;
                    if(gui.getSlotExecuteIDs().containsKey(event.getSlot())) {
                        executeID = gui.getSlotExecuteIDs().get(event.getSlot());
                    }else {
                        executeID = Integer.MIN_VALUE;
                    }
                    gui.onClick(executeID, event);
                    if(this.closeOnClick) {
                        event.getWhoClicked().closeInventory();
                    }
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        for(SimpleGui gui : Lists.newArrayList(this.guiList)) {
            if(gui.getGui() != null) {
                if (gui.getGui().equals(event.getInventory())) {
                    this.guiList.remove(gui);
                    break;
                }
            }
        }
    }

    public void onUpdate() {

    }

    public int getGuiSize() {
        return guiSize;
    }

    public Inventory getGui() {
        return gui;
    }

    public String getGuiTitle() {
        return guiTitle;
    }

    public boolean isCancelClick() {
        return cancelClick;
    }

    public HashMap<Integer, ItemStack> getSlotItems() {
        return slotItems;
    }

    public HashMap<Integer, Integer> getSlotExecuteIDs() {
        return slotExecuteIDs;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Player getOpener() {
        return opener;
    }
}
