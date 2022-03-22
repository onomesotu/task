package com.example.task.client;

import com.example.task.model.RandomUsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonClient {

  private final AsyncHttpClient asyncHttpClient;
  private final ObjectMapper objectMapper;

  public RandomUsers fetchRandomPersons()
      throws ExecutionException, InterruptedException, JsonProcessingException {
    Response personsResponseFuture = asyncHttpClient.prepareGet("https"
            + "://randomuser"
            + ".me/api?results=20")
        .execute()
        .toCompletableFuture().get();

    return objectMapper.readValue(personsResponseFuture.getResponseBody(), RandomUsers.class);
  }
}
