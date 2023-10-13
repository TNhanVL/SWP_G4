/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mservice.momo;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.LogUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author TTNhan
 */
public class MomoPay {

    public static String getPayLink(HttpServletRequest request, RequestType requestType, int userID, String[] courseIDs, long amount) {
        String payLink = null;

        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        Long transId = 2L;

        String partnerClientId = "partnerClientId";

        //description pay
        String orderInfo = "Pay With MoMo";
        if (courseIDs != null) {
            if (courseIDs.length == 1) {
                orderInfo = "Pay 1 course";
            } else {
                orderInfo = "Pay " + courseIDs.length + " courses";
            }
        }

        //link after pay success
        String returnURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/finishedPayment?userID=" + userID;
        if (courseIDs != null) {
            for (String courseID : courseIDs) {
                returnURL += "&course=" + courseID;
            }
        }
        String notifyURL = "https://google.com.vn";
        String callbackToken = "callbackToken";
        String token = "";

        Environment environment = Environment.selectEnv("dev");

        try {
            if (requestType == RequestType.CAPTURE_WALLET) {

                PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
                payLink = captureWalletMoMoResponse.getPayUrl();
            } else {
                orderId = String.valueOf(System.currentTimeMillis());
                requestId = String.valueOf(System.currentTimeMillis());
                PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, null);
                payLink = captureATMMoMoResponse.getPayUrl();
            }
        } catch (Exception ex) {
            Logger.getLogger(MomoPay.class.getName()).log(Level.SEVERE, null, ex);
        }

        return payLink;
    }
}
