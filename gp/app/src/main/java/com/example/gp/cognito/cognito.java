package com.example.gp.cognito;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class cognito {

    private final String userPoolId = "ap-southeast-2_uxjCc7pAA";
    private final String clientId = "765b0tjrlfkeiej01vtmgv5gc4";
    private final String clientSecret = "1kb2gjnl0oncvkno6bvbhg628rhvfhgqm82kpb186cq0l7lg2ci3";
    private final Regions region = Regions.AP_SOUTHEAST_2;

    private Context context;

    public void cognitoSettings(Context context) {
        this.context = context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRegion() {
        return region.getName();
    }

    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, userPoolId, clientId, clientSecret, region);
    }
}
