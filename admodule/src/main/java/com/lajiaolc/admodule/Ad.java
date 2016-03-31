package com.lajiaolc.admodule;

import android.app.Activity;

import com.bb.dd.BeiduoPlatform;
import com.dc.wall.DianCai;
import com.zy.phone.SDKInit;


/**
 * Created by Administrator on 2016/3/28.
 */
public class Ad {
    public static void initAd(Activity activity) {
        net.midi.wall.sdk.AdWall.init(activity, "6", "6666666666666666");

        net.youmi.android.AdManager.getInstance(activity).init("cfdbdd2786ea88ea", "d8edde7d10dd0073");

        com.ncc.sdk.offerwall.NccOfferWallAPI.setPlatformId("13872c5a3cfafb1e639b619c3a25c7ab");
        com.ncc.sdk.offerwall.NccOfferWallAPI.init(activity);


         String AdpCode = "e9ff112bc855bf4c";
        //private String AdpCode = "4e368780d0c76f31";
        // 用户ID，用于记录开发者应用的唯一用户标识,没有为空
         String Other = "";
        com.zy.phone.SDKInit.initAd(activity, AdpCode, "");

        com.dc.wall.DianCai.initApp(activity, "168", "666666888888");

        com.bb.dd.BeiduoPlatform.setAppId(activity, "10003", "");
        com.bb.dd.BeiduoPlatform.setUserId("6666");
    }
}
