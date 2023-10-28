package com.mservice.processor;

import com.mservice.config.Environment;
import com.mservice.enums.Language;
import com.mservice.models.HttpResponse;
import com.mservice.models.PaymentRequest;
import com.mservice.models.PaymentResponse;
import com.mservice.models.PaymentTokenRequest;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

public class PayByToken extends AbstractProcess<PaymentTokenRequest, PaymentResponse> {
    public PayByToken(Environment environment) {
        super(environment);
    }

    public static PaymentResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo,
                                   String returnUrl, String notifyUrl, String extraData, String partnerClientId, String token, Boolean autoCapture) {
        try {
            PayByToken m2Processor = new PayByToken(env);

            PaymentTokenRequest request = m2Processor.createTokenPaymentRequest(orderId, requestId, amount, orderInfo,
                    returnUrl, notifyUrl, extraData, partnerClientId, token, autoCapture);
            PaymentResponse captureMoMoResponse = m2Processor.execute(request);

            return captureMoMoResponse;
        } catch (Exception exception) {
            LogUtils.error("[PayByTokenProcess] "+ exception);
        }
        return null;
    }

    @Override
    public PaymentResponse execute(PaymentTokenRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, PaymentTokenRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getTokenPayUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[PaymentResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            PaymentResponse paymentResponse = getGson().fromJson(response.getData(), PaymentResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + paymentResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + paymentResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + paymentResponse.getMessage() +
                    "&" + Parameter.PAY_URL + "=" + paymentResponse.getPayUrl() +
                    "&" + Parameter.RESULT_CODE + "=" + paymentResponse.getResultCode();

            LogUtils.info("[PaymentMoMoResponse] rawData: " + responserawData);

            return paymentResponse;

        } catch (Exception exception) {
            LogUtils.error("[PaymentMoMoResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public PaymentTokenRequest createTokenPaymentRequest(String orderId, String requestId, String amount, String orderInfo,
                                                         String returnUrl, String notifyUrl, String extraData, String partnerClientId, String token, Boolean autoCapture) {
        try {
            String requestRawData = Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&" +
                    Parameter.AMOUNT + "=" + amount + "&" +
                    Parameter.EXTRA_DATA + "=" + extraData + "&" +
                    Parameter.ORDER_ID + "=" + orderId + "&" +
                    Parameter.ORDER_INFO + "=" + orderInfo + "&" +
                    Parameter.PARTNER_CLIENT_ID + "=" + partnerClientId + "&" +
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&" +
                    Parameter.REQUEST_ID + "=" + requestId + "&" +
                    Parameter.TOKEN + "=" + token;

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[PaymentTokenRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new PaymentTokenRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, "MoMo Store", partnerClientId, token, Long.valueOf(amount), "test StoreId",
                    returnUrl, notifyUrl, orderInfo, extraData, autoCapture, null, signRequest);
        } catch (Exception e) {
            LogUtils.error("[PaymentTokenRequest] "+ e);
        }

        return null;
    }
}
