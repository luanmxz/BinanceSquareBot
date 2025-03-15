package com.luanmxz.BinanceSquarePostBot.infrastructure;

import org.apache.commons.codec.binary.Base32;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;

import com.google.protobuf.InvalidProtocolBufferException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import com.Authenticator;

public class GoogleAuthDecoder {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String OTPAUTH_MIGRATION_URL = dotenv.get("OTPAUTH_MIGRATION_URL");

    public String decode() throws UnsupportedEncodingException, InvalidProtocolBufferException {
        List<Entry<String, String>> parsedUrl = parseUrl(OTPAUTH_MIGRATION_URL);
        byte[] decodedData = Base64.getDecoder().decode(parsedUrl.getFirst().getValue());
        Authenticator.MigrationPayload payload = Authenticator.MigrationPayload.parseFrom(decodedData);
        Base32 base32 = new Base32();

        String secretKey = base32.encodeToString(payload.getOtpParametersList().getFirst().getSecret().toByteArray())
                .replace("=",
                        "");

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secretKey).build();

        String TOTPCode = String.valueOf(gAuth.getTotpPassword(key.getKey()));

        return TOTPCode;
    }

    /**
     * Realiza o parse da URL e retorna uma lista de key-value pairs.
     * 
     * @param url A URL a ser analisada.
     * @return Uma lista de key-value pairs representando os parâmetros da URL.
     * @throws UnsupportedEncodingException Se ocorrer um erro ao decodificar a URL.
     */
    private List<Entry<String, String>> parseUrl(String url) throws UnsupportedEncodingException {
        List<Entry<String, String>> urlPairs = new ArrayList<>();
        String[] pairs = url.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            urlPairs.add(new SimpleEntry<>(key, value));
        }
        return urlPairs;
    }
}
