package com.gmail.gogobebe2.coinstomail;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling " + this.getName() + ". If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("cashout")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Error! You need to be a player to use this command!");
                return true;
            }

            Player player = (Player) sender;

            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Error! Incorrect command usage, use it like: "
                        + ChatColor.AQUA + "/cashout <number of coins> <paypal email>");
                return true;
            }

            // Email variables:
            int numberOfCoins;
            HashMap<Integer, ? extends ItemStack> coins = player.getInventory().all(Material.EMERALD);
            int coinsOwned = 0;
            String serverOwnerEmail = getConfig().getString("yourEmail");
            String paypalEmail;
            String username = getConfig().getString("username");
            String password = getConfig().getString("password");
            String host = getConfig().getString("host");
            String subject = "CoinsToMail cashout for " + player.getName();
            String text;

            for (ItemStack coin : coins.values()) coinsOwned += coin.getAmount();

            try {
                numberOfCoins = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException nfe) {
                player.sendMessage(ChatColor.RED + "Error! " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " is not a number!");
                return true;
            }

            if (numberOfCoins <= 0) {
                player.sendMessage(ChatColor.RED + "Error! " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " is not suitable number!");
                return true;
            }

            paypalEmail = args[1];

            if (numberOfCoins > coinsOwned) {
                player.sendMessage(ChatColor.RED + "Error! You don't have " + numberOfCoins + " coins!");
                return true;
            }

            text = "Coins: " + numberOfCoins + ", Paypal email: " + paypalEmail;

            // debug:
            removeCoins(numberOfCoins, player.getInventory());

            if (sendMail(serverOwnerEmail, paypalEmail, username, password, host, subject, text)) {
                getLogger().log(Level.INFO, "Sent email '" + text + "' with the subject '" + subject + "'.");
                removeCoins(numberOfCoins, player.getInventory());
                player.sendMessage(ChatColor.GREEN + "Cashout email has been sent with the email " + paypalEmail
                        + " for " + numberOfCoins + " coins.");
            }
            else player.sendMessage(ChatColor.RED + "An error occurred while sending the cashout email.");

            return true;
        }
        return false;
    }

    private void removeCoins(int amount, PlayerInventory inventory) {
        HashMap<Integer, ? extends ItemStack> coins = inventory.all(Material.EMERALD);

        for (ItemStack coin : coins.values()) {
            amount-= coin.getAmount();
            if (amount <= 0) {
                coin.setAmount(amount * -1);
                break;
            }
            else coin.setAmount(0);
        }
    }

    /**
     *
     * @param to Recipient's email ID needs to be mentioned.
     * @param from Sender's email ID needs to be mentioned.
     * @param USERNAME Sender's email ID needs to be mentioned.
     * @param PASSWORD Sender's email ID needs to be mentioned.
     * @param host What you're sending the email through.
     * @param subject Email subject.
     * @param text Email text.
     * @return If it was successful or not.
     */
    private boolean sendMail(String to, String from, final String USERNAME, final String PASSWORD, String host, String subject, String text) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);

            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
    }

}
