package de.fr3qu3ncy.mute.storage;

import de.fr3qu3ncy.easyconfig.core.annotations.ConfigPath;
import de.fr3qu3ncy.mute.MutePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataStorage {

    private DataStorage() {}

    @ConfigPath("muted_players")
    public static List<MuteRecord> MUTED_PLAYERS = new ArrayList<>();

    public static MuteRecord findRecord(UUID uuid) {
        return MUTED_PLAYERS.stream()
            .filter(muteRecord -> muteRecord.getUuid().equalsIgnoreCase(uuid.toString()))
            .findFirst()
            .orElse(null);
    }

    public static void removeRecord(MuteRecord muteRecord) {
        MUTED_PLAYERS.remove(muteRecord);
        MutePlugin.getInstance().getDataStorage().getConfigIO().set("muted_players", MUTED_PLAYERS);
    }

    public static void addRecord(MuteRecord muteRecord) {
        MUTED_PLAYERS.add(muteRecord);
        MutePlugin.getInstance().getDataStorage().getConfigIO().set("muted_players", MUTED_PLAYERS);
    }
}
