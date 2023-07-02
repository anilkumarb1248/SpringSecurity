package oauth.keycloack.client.credentials.flow.microservice3.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * RequestHeadersInterceptor is used to intercept the service calls and adds the additional headers.
 */
public class RequestHeadersInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHeadersInterceptor.class);

    public RequestHeadersInterceptor(){
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try{
            HttpHeaders httpHeaders = request.getHeaders();
            httpHeaders.add("SessionId", "Service3_SessionId"+ LocalDateTime.now());
            httpHeaders.add("txnId", "Service3_TxnId"+ LocalDateTime.now());
        }catch (Exception e){
            LOGGER.error("Exception occurred while adding additional headers, ex: {}", e.getMessage());
        }
        return execution.execute(request,body);
    }
}
