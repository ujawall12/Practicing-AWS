package com.cloud.learningaws.util;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.lambda.model.LambdaException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LambdaService {

    private final AWSLambda lambdaClient;
    public void invokeLambdaFunction(String functionName, String payLoad) {

        try{
            InvokeRequest request = new InvokeRequest()
                    .withFunctionName(functionName)
                    .withPayload(payLoad);

            InvokeResult result = lambdaClient.invoke(request);
            log.info("----->LambdaService----->invokeLambdaFunction----->result: {}", result.getStatusCode());
        }
        catch (LambdaException e) {
            e.printStackTrace();
        }
    }
}


