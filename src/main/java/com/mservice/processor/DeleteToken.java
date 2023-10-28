package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.models.*;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

public class DeleteToken extends AbstractProcess<DeleteTokenRequest, DeleteTokenResponse> {
    public DeleteToken(Environment environment) {
        super(environment);
    }

    public static DeleteTokenResponse process(Environment env, String requestId, String orderId, String partnerClientId, String token) {
        try {
            DeleteToken m2Processor = new DeleteToken(env);

            DeleteTokenRequest request = m2Processor.createDeleteTokenRequest(orderId, requestId, partnerClientId, token);
            DeleteTokenResponse response = m2Processor.execute(request);

            return response;
        } catch (Exception exception) {
            LogUtils.error("[DeleteTokenProcess] "+ exception);
        }
        return null;
    }

    @Override
    public DeleteTokenResponse execute(DeleteTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, DeleteTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenDeleteUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[DeleteTokenResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            DeleteTokenResponse deleteTokenResponse = getGson().fromJson(response.getData(), DeleteTokenResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" +
                    "&" + Parameter.ORDER_ID + "=" + deleteTokenResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + deleteTokenResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + deleteTokenResponse.getResultCode();

            LogUtils.info("[DeleteTokenResponse] rawData: " + responserawData);

            return deleteTokenResponse;

        } catch (Exception exception) {
            LogUtils.error("[DeleteTokenResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public DeleteTokenRequest createDeleteTokenRequest(String orderId, String requestId, String partnerClientId, String token) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.TOKEN + "=" + token;

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[DeleteTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new DeleteTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, partnerClientId, token, signRequest);
        } catch (Exception e) {
            LogUtils.error("[DeleteTokenRequest] "+ e);
        }

        return null;
    }
}
