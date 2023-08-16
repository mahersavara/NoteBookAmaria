package com.khuatlinh.notebookamaria;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {
    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static void loadNativeAds(Context context, NativeTemplateStyle style, TemplateView templateView){
        MobileAds.initialize(context);
        AdLoader adLoader = new AdLoader.Builder(context, context.getString(R.string.appId))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        templateView.setStyles(style);
                        templateView.setNativeAd(nativeAd);
                        templateView.setVisibility(View.VISIBLE);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    static CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid())
                .collection("my_notes");

    }
    static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:mm dd/MM/YYYY ").format(timestamp.toDate());
    }
}
