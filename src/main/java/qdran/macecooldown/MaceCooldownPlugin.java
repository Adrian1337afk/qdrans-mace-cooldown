package qdran.macecooldown;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MaceCooldownPlugin extends JavaPlugin implements Listener {

    private int cooldownTicks;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        cooldownTicks = toTicks(getConfig().getDouble("cooldown-seconds", 5.0));
        getServer().getPluginManager().registerEvents(this, this);
    }

    // Block mace hits while the mace is on cooldown.
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMaceHitCheck(EntityDamageByEntityEvent event) {
        Player player = maceAttacker(event);
        if (player != null && cooldownTicks > 0 && player.hasCooldown(Material.MACE)) {
            event.setCancelled(true);
        }
    }

    // Start the cooldown after a hit that went through.
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMaceHit(EntityDamageByEntityEvent event) {
        Player player = maceAttacker(event);
        if (player != null && cooldownTicks > 0) {
            player.setCooldown(Material.MACE, cooldownTicks);
        }
    }

    private Player maceAttacker(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player
                && player.getInventory().getItemInMainHand().getType() == Material.MACE) {
            return player;
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Current mace cooldown: " + cooldownTicks / 20.0 + "s. Usage: /" + label + " <seconds>");
            return true;
        }
        double seconds;
        try {
            seconds = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Not a number: " + args[0]);
            return true;
        }
        if (seconds < 0 || seconds > 3600) {
            sender.sendMessage("Seconds must be between 0 and 3600.");
            return true;
        }
        cooldownTicks = toTicks(seconds);
        getConfig().set("cooldown-seconds", seconds);
        saveConfig();
        sender.sendMessage(cooldownTicks == 0
                ? "Mace cooldown disabled."
                : "Mace cooldown set to " + seconds + "s.");
        return true;
    }

    private static int toTicks(double seconds) {
        return (int) Math.round(Math.max(0, seconds) * 20);
    }
}
