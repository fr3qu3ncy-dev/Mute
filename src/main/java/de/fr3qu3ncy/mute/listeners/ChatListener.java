package de.fr3qu3ncy.mute.listeners;

import de.fr3qu3ncy.mute.storage.DataStorage;
import de.fr3qu3ncy.mute.storage.MuteRecord;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        MuteRecord muteRecord = DataStorage.findRecord(player.getUniqueId());

        if (muteRecord == null) return;

        long endTime = muteRecord.getEndTime();
        if (System.currentTimeMillis() > endTime && muteRecord.isTemporary()) {
            DataStorage.removeRecord(muteRecord);
            return;
        }

        event.setCancelled(true);

        String reason = muteRecord.getReason();
        if (!reason.isBlank()) {
            player.sendMessage("§cYou are currently muted for §f" + reason + "§c.");
        } else {
            player.sendMessage("§cYou are currently muted.");
        }
    }
}
