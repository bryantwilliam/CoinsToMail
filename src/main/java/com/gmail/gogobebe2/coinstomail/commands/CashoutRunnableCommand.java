package com.gmail.gogobebe2.coinstomail.commands;

import com.gmail.gogobebe2.coinstomail.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class CashoutRunnableCommand extends RunnableCommand {

    public CashoutRunnableCommand(CommandSender sender, String[] args) {
        super(sender, args);
    }

    @Override
    public void run() {
        Player player;
        if (getSender() instanceof Player) player = (Player) getSender();
        else {
            getSender().sendMessage(ChatColor.RED + "Error! You need to be a player to use this command!");
            return;
        }

        if (getArgs().length != 2) {
            player.sendMessage(ChatColor.RED + "Error! Incorrect command usage, use it like: "
                    + ChatColor.AQUA + "/cashout <number of coins> <paypal email>");
            return;
        }

        Main main = Main.getInstance();

        // Email variables:
        int numberOfCoins;
        HashMap<Integer, ? extends ItemStack> coins = player.getInventory().all(Material.EMERALD);
        int coinsOwned = 0;
        String serverOwnerEmail = main.getConfig().getString("yourEmail");
        String paypalEmail;
        String username = main.getConfig().getString("username");
        String password = main.getConfig().getString("password");
        String host = main.getConfig().getString("host");
        String port = main.getConfig().getString("port");
        String subject = "CoinsToMail cashout for " + player.getName();
        String text;

        for (ItemStack coin : coins.values()) coinsOwned += coin.getAmount();

        try {
            numberOfCoins = Integer.parseInt(getArgs()[0]);
        }
        catch (NumberFormatException nfe) {
            player.sendMessage(ChatColor.RED + "Error! " + ChatColor.DARK_RED + getArgs()[0] + ChatColor.RED + " is not a number!");
            return;
        }

        if (numberOfCoins <= 0) {
            player.sendMessage(ChatColor.RED + "Error! " + ChatColor.DARK_RED + getArgs()[0] + ChatColor.RED + " is not suitable number!");
            return;
        }

        paypalEmail = getArgs()[1];

        if (numberOfCoins > coinsOwned) {
            player.sendMessage(ChatColor.RED + "Error! You don't have " + numberOfCoins + " coins!");
            return;
        }

        text = "Coins: " + numberOfCoins + ", Paypal email: " + paypalEmail;

        if (sendMail(serverOwnerEmail, paypalEmail, username, password, host, port, subject, text)) {
            main.getLogger().log(Level.INFO, "Sent email '" + text + "' with the subject '" + subject + "'.");
            removeCoins(numberOfCoins, player.getInventory());
            player.sendMessage(ChatColor.GREEN + "Cashout email has been sent with the email " + paypalEmail
                    + " for " + numberOfCoins + " coins.");
        }
        else player.sendMessage(ChatColor.RED + "An error occurred while sending the cashout email.");
    }

    /**
     * @return If it was successful or not.
     */
    private boolean sendMail(String to, String from, final String USERNAME, final String PASSWORD, String host, String port,
                             String subject, String text) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

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

    public static void removeCoins(int amountLeftToTakeAway, PlayerInventory inventory) {
        HashMap<Integer, ? extends ItemStack> coins = inventory.all(Material.EMERALD);

        for (ItemStack coin : coins.values()) {
            int coinAmount = coin.getAmount();

            if (amountLeftToTakeAway >= coinAmount) {
                amountLeftToTakeAway -= coinAmount;
                coin.setAmount(0);
            }
            else {
                coin.setAmount(coinAmount - amountLeftToTakeAway);
                break;
            }
        }
    }
}
