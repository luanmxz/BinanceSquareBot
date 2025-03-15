package com.luanmxz.BinanceSquarePostBot.services;

import java.util.LinkedHashMap;
import java.util.Map;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;

import io.github.cdimascio.dotenv.Dotenv;

public class BinanceService {
    private static Dotenv dotenv = Dotenv.load();

    public SpotClient createClient() {
        SpotClient client = new SpotClientImpl(dotenv.get("API_KEY"), dotenv.get("API_SECRET"));

        return client;
    }

    public String searchValues(SpotClient client, String symbol, String interval, int limit) {
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol); // "BTCUSDT"
        parameters.put("interval", interval); // "1m"
        parameters.put("limit", limit); // Pegando os últimos x (exemplo 50) preços
        String response = client.createMarket().klines(parameters);
        System.out.println(response);
        return response;
    }
}
