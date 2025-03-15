package com.luanmxz.BinanceSquarePostBot;

import java.io.UnsupportedEncodingException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.luanmxz.BinanceSquarePostBot.application.Orchestrator;
import com.luanmxz.BinanceSquarePostBot.infrastructure.ApplicationContext;

public class BinanceSquarePostBotApplication {

	public static void main(String[] args) throws UnsupportedEncodingException, InvalidProtocolBufferException {
		Orchestrator orchestrator = new Orchestrator(ApplicationContext.getBinanceService(),
				ApplicationContext.getSeleniumService());
		orchestrator.execute();
	}
}
