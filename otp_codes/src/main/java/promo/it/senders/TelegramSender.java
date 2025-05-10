package promo.it.senders;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import promo.it.settings.SettingsManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TelegramSender extends BaseSender {
    private String tgUrl;
    public TelegramSender(SettingsManager sm) {
        super(sm);
        tgUrl = "https://api.telegram.org/bot" + sm.getBotToken() + "/sendMessage";

    }

    private void sendTelegramRequest(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode != 200) {
//                    logger.error("Telegram API error. Status code: {}", statusCode);
//                } else {
//                    logger.info("Telegram message sent successfully");
//                }
            }
        } catch (IOException e) {
            //logger.error("Error sending Telegram message: {}", e.getMessage());
        }
    }
    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    @Override
    public void send(String code) {
        String message = String.format(settingsManager.getTgUsername()
                + ", your confirmation code is: %s", code);
        String url = String.format("%s?chat_id=%s&text=%s",
                tgUrl,
                settingsManager.getChatId(),
                urlEncode(message));

        sendTelegramRequest(url);
    }
}
