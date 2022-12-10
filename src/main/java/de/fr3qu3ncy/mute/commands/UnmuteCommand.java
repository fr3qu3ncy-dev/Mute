package de.fr3qu3ncy.mute.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import de.fr3qu3ncy.mute.storage.DataStorage;
import de.fr3qu3ncy.mute.storage.MuteRecord;
import org.bukkit.entity.Player;

@CommandAlias("unmute")
public class UnmuteCommand extends BaseCommand {

    @Default
    @CommandPermission("mute.unmute")
    public static void onUnmute(Player sender, OnlinePlayer mutedPlayer) {
        MuteRecord muteRecord = DataStorage.findRecord(mutedPlayer.getPlayer().getUniqueId());
        if (muteRecord == null) {
            sender.sendMessage("§cThat player is not muted!");
            return;
        }

        DataStorage.removeRecord(muteRecord);
        sender.sendMessage("§aPlayer has been unmuted.");
    }
}
