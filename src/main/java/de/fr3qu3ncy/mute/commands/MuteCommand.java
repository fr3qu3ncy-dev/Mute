package de.fr3qu3ncy.mute.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import de.fr3qu3ncy.mute.gui.ConfirmGUI;
import de.fr3qu3ncy.mute.storage.DataStorage;
import de.fr3qu3ncy.mute.util.TimeUtils;
import org.bukkit.entity.Player;

@CommandAlias("mute|tempmute")
public class MuteCommand extends BaseCommand {

    @Default
    @CommandPermission("mute.use")
    @CommandCompletion(" duration reason")
    public static void onMute(Player sender, OnlinePlayer mutedPlayer, @Optional String duration, @Single @Optional String reason) {
        if (!checkPlayer(sender, mutedPlayer)) return;

        new ConfirmGUI(sender, mutedPlayer.getPlayer(),
            duration == null || duration.isBlank() ? 0 : TimeUtils.parseDuration(duration),
            reason).open();
    }

    private static boolean checkPlayer(Player sender, OnlinePlayer mutedPlayer) {
        if (DataStorage.findRecord(mutedPlayer.getPlayer().getUniqueId()) != null) {
            sender.sendMessage("§cThat player is already muted!");
            return false;
        }
        return true;
    }
}
