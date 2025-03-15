package com.luanmxz.BinanceSquarePostBot.application;

import java.io.UnsupportedEncodingException;

import com.binance.connector.client.SpotClient;
import com.google.protobuf.InvalidProtocolBufferException;
import com.luanmxz.BinanceSquarePostBot.infrastructure.GoogleAuthDecoder;
import com.luanmxz.BinanceSquarePostBot.infrastructure.TwilioSMSReader;
import com.luanmxz.BinanceSquarePostBot.services.BinanceService;
import com.luanmxz.BinanceSquarePostBot.services.SeleniumService;

public class Orchestrator {

    private final BinanceService binanceService;
    private final SeleniumService seleniumService;

    public Orchestrator(BinanceService binanceService, SeleniumService seleniumService) {
        this.binanceService = binanceService;
        this.seleniumService = seleniumService;
    }

    public void execute() throws UnsupportedEncodingException, InvalidProtocolBufferException {
        SpotClient client = binanceService.createClient();
        binanceService.searchValues(client, "BTCUSDT", "1m", 50);

        seleniumService.execute();

        TwilioSMSReader.execute(null);
        GoogleAuthDecoder googleAuthDecoder = new GoogleAuthDecoder();
        System.out.println("GAuth code -> " + googleAuthDecoder.decode());
    }
}
