package com.camlait.global.erp.delegate.util;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * EntityHelper for http calls.
 *
 * @param <T> Expected response body type for http calls.
 */
@Component
public class HttpHelper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

    @Value("${rest.auth.header}")
    private String authHeader;

    private final RestTemplate restTemplate;

    @Autowired
    public HttpHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Perform an HTTP request using the POST verb.
     * 
     * @param url Resource URL.
     * @param body incoming body request.
     * @param clazz Expected response body type
     * @return An ResponseEntity object that contains the response as a body.
     */
    public ResponseEntity<T> doPost(String url, String body, Class<T> clazz, String requestId) {
        try {
            return restTemplate.exchange(url, HttpMethod.POST, buildEntity(requestId, (headers) -> {
                return new HttpEntity<String>(body, headers);
            }), clazz);
        } catch (Exception e) {
            LOGGER.error("Request perform unsucessfully. requestBody={}, url={}", body, url, e);
            throw e;
        }
    }

    /**
     * Perform an http request using the GET verb.
     * 
     * @param url Resource URL.
     * @param clazz Expected response body type
     * @return An ResponseEntity object that contains the response as a body.
     */

    public ResponseEntity<T> doGet(String url, Class<T> clazz, String requestId) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, buildEntity(requestId, (headers) -> {
                return new HttpEntity<String>(headers);
            }), clazz);
        } catch (Exception e) {
            LOGGER.error("Request perform unsucessfully. url={}", url, e);
            throw e;
        }
    }

    /**
     * Perform an http request using the PUT verb.
     *
     * @param url Resource URL
     * @param body Body of the request
     * @param clazz Expected type of the response
     * @return
     */
    public ResponseEntity<T> doPut(String url, String body, Class<T> clazz, String requestId) {
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, buildEntity(requestId, (headers) -> {
                return new HttpEntity<String>(body, headers);
            }), clazz);
        } catch (Exception e) {
            LOGGER.error("Request perform unsucessfully. requestBody={}, url={}", body, url, e);
            throw e;
        }
    }

    /**
     * Perform an http request using the DELETE verb.
     *
     * @param url Resource URL
     * @param clazz Expected type of the response
     * @return
     */
    public ResponseEntity<T> doDelete(String url, Class<T> clazz, String requestId) {
        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, buildEntity(requestId, (headers) -> {
                return new HttpEntity<String>(headers);
            }), clazz);
        } catch (Exception e) {
            LOGGER.error("Request perform unsucessfully. requestBody={}, url={}", url, e);
            throw e;
        }
    }

    /**
     * Build an HttpEntity object that contain the authentification header and the body of the request to send.
     * 
     * @param requestId request identifier.
     * @param entityBuilder generic function that help to build the HttpEntity object to send.
     * @return
     */
    private HttpEntity<String> buildEntity(String requestId, Function<HttpHeaders, HttpEntity<String>> entityBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);
        final String rId = requestId == null ? UUID.randomUUID().toString() : requestId;
        headers.set("request-id", rId);
        return entityBuilder.apply(headers);
    }
}
