package com.dumptruckman.spamhammer.command;

import com.dumptruckman.spamhammer.SpamHammerPlugin;
import com.dumptruckman.spamhammer.util.Logging;
import com.dumptruckman.spamhammer.util.Perm;
import com.dumptruckman.spamhammer.util.locale.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Enables debug-information.
 */
public class DebugCommand extends PluginCommand {

    public DebugCommand(SpamHammerPlugin plugin) {
        super(plugin);
        this.setName("Turn Debug on/off?");
        this.setCommandUsage("/spam debug" + ChatColor.GOLD + " [1|2|3|off]");
        this.setArgRange(0, 1);
        this.addKey("spam debug");
        this.addKey("spam d");
        this.addKey("spamdebug");
        this.addKey("spamd");
        this.addCommandExample("/pt debug " + ChatColor.GOLD + "2");
        this.setPermission(Perm.COMMAND_DEBUG.getPermission());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            if (args.get(0).equalsIgnoreCase("off")) {
                plugin.getSettings().setGlobalDebug(0);
                plugin.getSettings().save();
            } else {
                try {
                    int debugLevel = Integer.parseInt(args.get(0));
                    if (debugLevel > 3 || debugLevel < 0) {
                        throw new NumberFormatException();
                    }
                    plugin.getSettings().setGlobalDebug(debugLevel);
                    plugin.getSettings().save();
                } catch (NumberFormatException e) {
                    messager.bad(Message.INVALID_DEBUG, sender);
                }
            }
        }
        this.displayDebugMode(sender);
    }

    private void displayDebugMode(CommandSender sender) {
        if (plugin.getSettings().getGlobalDebug() == 0) {
            messager.normal(Message.DEBUG_SET, sender, ChatColor.RED + messager.getMessage(Message.GENERIC_OFF));
        } else {
            messager.normal(Message.DEBUG_SET, sender, ChatColor.GREEN
                    + Integer.toString(plugin.getSettings().getGlobalDebug()));
            Logging.fine("SimpleCircuits Debug ENABLED");
        }
    }
}
