package oauth.keycloack.client.credentials.flow.microservice3.interceptors;

import oauth.keycloack.client.credentials.flow.microservice3.config.gateway.GatewayServiceTokenKeeper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class GatewayTokenInterceptor implements ClientHttpRequestInterceptor {

    public GatewayServiceTokenKeeper tokenKeeper;

    public GatewayTokenInterceptor(GatewayServiceTokenKeeper tokenKeeper) {
        this.tokenKeeper = tokenKeeper;
    }

    public GatewayServiceTokenKeeper getTokenKeeper() {
        return tokenKeeper;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders httpHeaders = tokenKeeper.getGatewayHeaders();
        if (httpHeaders != null) {
            request.getHeaders().setAll(httpHeaders.toSingleValueMap());
        }
        return execution.execute(request, body);
    }
}
