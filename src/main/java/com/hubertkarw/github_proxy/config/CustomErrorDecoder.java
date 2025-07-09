package com.hubertkarw.github_proxy.config;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;


public class CustomErrorDecoder implements ErrorDecoder {
    ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String s, Response response) {

        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        if (status == 500 || status == 503) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    50L,
                    response.request()
            );
        }
        return errorDecoder.decode(s, response);
    }

}
