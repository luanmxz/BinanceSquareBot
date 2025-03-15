package com.luanmxz.BinanceSquarePostBot.infrastructure;

import com.luanmxz.BinanceSquarePostBot.services.BinanceService;
import com.luanmxz.BinanceSquarePostBot.services.SeleniumService;

public class ApplicationContext {

    // Using Double-Checked Locking
    private static volatile BinanceService binanceService; // volatile para evitar problemas de cache entre threads
    private static volatile SeleniumHandler seleniumHandler;
    private static final Object LOCK = new Object();

    public static BinanceService getBinanceService() {
        if (binanceService == null) { // Segundo check (com bloqueio)
            synchronized (LOCK) {// Bloqueio para garantir exclusividade
                if (binanceService == null) { // Segundo check (com bloqueio)
                    binanceService = new BinanceService();
                }
            }
        }

        return binanceService;
    }

    private static SeleniumHandler getSeleniumHandler() {
        if (seleniumHandler == null) {
            synchronized (LOCK) {
                if (seleniumHandler == null) {
                    seleniumHandler = new SeleniumHandler();
                }
            }
        }
        return seleniumHandler;
    }

    public static SeleniumService getSeleniumService() {
        return new SeleniumService(getSeleniumHandler());
    }
}
