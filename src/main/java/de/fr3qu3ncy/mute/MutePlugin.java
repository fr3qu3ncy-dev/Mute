package de.fr3qu3ncy.mute;

import co.aikar.commands.PaperCommandManager;
import de.fr3qu3ncy.easyconfig.core.EasyConfig;
import de.fr3qu3ncy.easyconfig.core.registry.ConfigRegistry;
import de.fr3qu3ncy.easyconfig.spigot.SpigotConfig;
import de.fr3qu3ncy.easytools.spigot.EasyTools;
import de.fr3qu3ncy.mute.commands.MuteCommand;
import de.fr3qu3ncy.mute.commands.UnmuteCommand;
import de.fr3qu3ncy.mute.listeners.ChatListener;
import de.fr3qu3ncy.mute.storage.DataStorage;
import de.fr3qu3ncy.mute.storage.MuteRecord;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MutePlugin extends JavaPlugin {

    @Getter
    private EasyConfig dataStorage;

    @Override
    public void onEnable() {
        ConfigRegistry.register(MuteRecord.class);
        this.dataStorage = new SpigotConfig(getDataFolder(), "mutes", DataStorage.class);
        this.dataStorage.load();

        registerListeners();
        registerCommands();

        EasyTools.setInternalUse(this);
        EasyTools.enableGUISystem();
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new MuteCommand());
        commandManager.registerCommand(new UnmuteCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public static MutePlugin getInstance() {
        return getPlugin(MutePlugin.class);
    }
}
