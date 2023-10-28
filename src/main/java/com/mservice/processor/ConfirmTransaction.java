package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.ConfirmRequestType;
import com.mservice.enums.Language;
import com.mservice.models.*;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

public class ConfirmTransaction extends AbstractProcess<ConfirmRequest, ConfirmResponse> {
    public ConfirmTransaction(Environment environment) {
        super(environment);
    }

    public static ConfirmResponse process(Environment env, String orderId, String requestId, String amount, ConfirmRequestType requestType, String description) throws Exception {
        try {
            ConfirmTransaction m2Processor = new ConfirmTransaction(env);

            ConfirmRequest request = m2Processor.createConfirmTransactionRequest(orderId, requestId, amount, requestType, description);
            ConfirmResponse response = m2Processor.execute(request);

            return response;
        } catch (Exception exception) {
            LogUtils.error("[ConfirmTransactionProcess] "+ exception);
        }
        return null;
    }

    @Override
    public ConfirmResponse execute(ConfirmRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, ConfirmRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getConfirmUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[ConfirmTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            ConfirmResponse confirmResponse = getGson().fromJson(response.getData(), ConfirmResponse.class);
            String responserawData =   Parameter.ORDER_ID + "=" + confirmResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + confirmResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + confirmResponse.getResultCode();

            LogUtils.info("[ConfirmTransactionResponse] rawData: " + responserawData);

            return confirmResponse;

        } catch (Exception exception) {
            LogUtils.error("[ConfirmTransactionResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params confirm MoMo Request");
        }
    }

    public ConfirmRequest createConfirmTransactionRequest(String orderId, String requestId, String amount, ConfirmRequestType requestType, String description) {

        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.DESCRIPTION + "=" + description + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.REQUEST_TYPE + "=" + requestType.getConfirmRequestType();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[ConfirmRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new ConfirmRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, Long.valueOf(amount), "", ConfirmRequestType.CAPTURE, signRequest);
        } catch (Exception e) {
            LogUtils.error("[ConfirmResponse] "+ e);
        }

        return null;
    }
}
