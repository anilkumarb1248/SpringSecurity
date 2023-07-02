package oauth.keycloack.client.credentials.flow.microservice3.config.gateway;

import oauth.keycloack.client.credentials.flow.microservice3.exceptions.HttpRedirectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Component
public class ServiceCallerErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected boolean hasError(HttpStatus statusCode) {
        return super.hasError(statusCode) || statusCode.series() == HttpStatus.Series.REDIRECTION;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus httpStatus = response.getStatusCode();
        if(httpStatus.series() == HttpStatus.Series.REDIRECTION){
            throw new HttpRedirectionException(httpStatus, response.getStatusText(), response.getHeaders(),
                    getResponseBody(response), getCharset(response));
        }
        super.handleError(response);
    }
}
