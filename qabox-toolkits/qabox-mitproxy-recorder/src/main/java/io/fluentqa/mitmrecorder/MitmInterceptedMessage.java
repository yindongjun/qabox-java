package io.fluentqa.mitmrecorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.appium.mitmproxy.InterceptedMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MitmInterceptedMessage {

    private Request request;

    private Response response;

    public MitmInterceptedMessage(InterceptedMessage message) {
        request = new Request(message);
        response = new Response(message);
    }

    @Data
    public static class Request {

        public Request(InterceptedMessage request) {
            method = request.requestMethod;
            url = request.requestURL.toString();
            body = request.requestBody;
            headers = new ArrayList<>();
            for (String[] header : request.requestHeaders) {
                if (header.length > 1) {
                    headers.add(new Header(header[0], header[1]));
                } else if (header.length > 0) {
                    headers.add(new Header(header[0]));
                }
            }
        }

        public String getUrl() {
            return url;
        }

        private String method;

        private String url;

        private List<Header> headers;

        private byte[] body;
    }

    @Data
    public static class Response {

        public Response(InterceptedMessage message) {
            statusCode = message.responseCode;
            body = message.responseBody;
            headers = new ArrayList<>();
            for (String[] header : message.responseHeaders) {
                if (header.length > 1) {
                    headers.add(new Header(header[0], header[1]));
                } else if (header.length > 0) {
                    headers.add(new Header(header[0]));
                }
            }
        }

        @JsonProperty("status_code")
        private int statusCode;

        private List<Header> headers;

        private byte[] body;
    }

    @Data
    public static class Header {

        public Header(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public Header(String name) {
            this.name = name;
            this.value = "";
        }

        private String name;

        private String value;
    }

}
