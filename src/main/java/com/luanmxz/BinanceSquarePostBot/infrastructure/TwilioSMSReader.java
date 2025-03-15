package com.luanmxz.BinanceSquarePostBot.infrastructure;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import io.github.cdimascio.dotenv.Dotenv;

public class TwilioSMSReader {
    private static final Dotenv dotenv = Dotenv.load();
    public static final String ACCOUNT_SID = dotenv.get("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = dotenv.get("TWILIO_AUTH_TOKEN");

    public static void execute(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Iterable<Message> messages = Message.reader().setTo(dotenv.get("TWILIO_PHONE_NUMBER")).read();
        for (Message message : messages) {
            System.out.println("De: " + message.getFrom() + " - " + message.getBody());
        }
    }
}
