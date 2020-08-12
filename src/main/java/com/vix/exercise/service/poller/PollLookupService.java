package com.vix.exercise.service.poller;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class PollLookupService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PollLookupService.class);

    public static String getVixDigitalResponse(String url) {
        final String[] result = {""};
        Observable.interval(3, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        result[0] = callExternalUrl(url);
                        return result[0];
                    }
                })
                .takeWhile(res -> result[0] == "")
                .subscribe();
        sleep(10000);

        System.out.println(result[0]);
        return result[0];

    }

    private static String callExternalUrl(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String result = "";

        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET().build();
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body().toString();
        } catch (IOException | InterruptedException | URISyntaxException uri) {
            uri.printStackTrace();
        }

        return result;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
