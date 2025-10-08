package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Task4
{
    public static void getHTTP() throws IOException, InterruptedException
    {
        try (HttpClient client = HttpClient.newHttpClient())
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://httpbin.org/anything"))
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(body);
            JsonNode headers = root.get("headers");

            if (headers != null && headers.has("Accept")) {
                String acceptValue = headers.get("Accept").asText();
                System.out.println("Допустимые типы ответа (Accept): " + acceptValue);
            } else {
                System.out.println("Заголовок 'Accept' не найден в ответе.");
            }
        }
    }
}

