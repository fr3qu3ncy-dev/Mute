package de.fr3qu3ncy.mute.gui;

import de.fr3qu3ncy.easytools.spigot.gui.GUIInventory;
import de.fr3qu3ncy.easytools.spigot.gui.item.GUIItem;
import de.fr3qu3ncy.mute.storage.DataStorage;
import de.fr3qu3ncy.mute.storage.MuteRecord;
import de.fr3qu3ncy.mute.util.TimeUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ConfirmGUI extends GUIInventory {

    private final Player mutedPlayer;
    private final long durationMillis;

    @Nullable
    private final String reason;

    public ConfirmGUI(Player player, Player mutedPlayer, long durationMillis, @Nullable String reason) {
        super(player, "Confirm Mute", 9);
        this.mutedPlayer = mutedPlayer;
        this.durationMillis = durationMillis;
        this.reason = reason;
    }

    @Override
    protected void addItems() {
        setItem(1, new GUIItem(Material.RED_WOOL)
            .setDisplayName("§cCancel")
            .setOnClick(ClickType.LEFT, this::cancel));
        setItem(7, new GUIItem(Material.GREEN_WOOL)
            .setDisplayName("§aConfirm")
            .setOnClick(ClickType.LEFT, this::confirm));
        setItem(4, new GUIItem(Material.PAPER)
            .setDisplayName("§fMuting §6" + mutedPlayer.getName())
            .setLore(getInfoLore()));
    }

    private List<String> getInfoLore() {
        List<String> lore = new ArrayList<>();
        lore.add("§fWould you like to mute §6" + mutedPlayer.getName());
        if (durationMillis > 0) lore.add("§ffor §6" + TimeUtils.prettyFormatTime(durationMillis));
        if (reason != null && !reason.isBlank()) lore.add("§ffor reason §6" + reason + "§f?");

        return lore;
    }

    private void cancel() {
        close();
        getPlayer().playSound(getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
    }

    private void confirm() {
        DataStorage.addRecord(new MuteRecord(
            mutedPlayer.getUniqueId().toString(),
            durationMillis != 0,
            System.currentTimeMillis() + durationMillis,
            reason != null ? reason : ""
        ));
        close();
        getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
        getPlayer().sendMessage("§aPlayer §6" + mutedPlayer.getName() + " §ahas been muted!");
    }
}
