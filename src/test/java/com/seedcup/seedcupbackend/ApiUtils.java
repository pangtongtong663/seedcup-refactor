package com.seedcup.seedcupbackend;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ApiUtils {
    public static MockHttpServletRequestBuilder postBuilder(String url, String host) {
        return MockMvcRequestBuilders.post(url)
                .header(HttpHeaders.HOST, host)
                .header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36")
                .header(HttpHeaders.CONNECTION, "keep-alive")
                .header(HttpHeaders.ACCEPT, "*/*")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder postBuilder(String url) {
        return postBuilder(url, "115.156.207.219");
    }

    public static MockHttpServletRequestBuilder getBuilder(String url, String host) {
        return MockMvcRequestBuilders.get(url)
                .header(HttpHeaders.HOST, host)
                .header(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36")
                .header(HttpHeaders.CONNECTION, "keep-alive")
                .header(HttpHeaders.ACCEPT, "*/*");
    }

    public static MockHttpServletRequestBuilder getBuilder(String url) {
        return getBuilder(url, "115.156.207.219");
    }

}
