package com.conpds.ocrenginedemo.java.ocrenginedemoapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.codevog.android.license_library.MainInteractor;
import com.codevog.android.license_library.MainInteractorImpl;
import com.codevog.android.license_library.client_side_exception.BaseOcrException;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

public class OcrModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext mContext;

    OcrModule(ReactApplicationContext context) {
        super(context);
        mContext = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "OcrModule";
    }

    @ReactMethod
    public void getOcrDatafromImage(
            String base64,
            String fileName,
            double fileSize,
            String type,
            String uri,
            Callback callBack
    ) {
        String finalUri = uri.replace("file://", "");

        String API_KEY = "YOUR_ANDROID_API_KEY";
        String LICENSE_KEY = "YOUR_ANDROID_LICENSE_KEY";
        MainInteractorImpl ocrEngine = new MainInteractorImpl(getSettingsPath());
        Bitmap src = BitmapFactory.decodeFile(finalUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String result = ocrEngine.recogFromBufferToJsonInSingleThread(API_KEY, LICENSE_KEY, data);
        Log.d("OCR Result : ", result);
        callBack.invoke(result);
    }

    private String getSettingsPath() {
        String path = mContext.getCacheDir().getParent() + "/settings";
        File folder = new File(path);
        if (!folder.exists()){
            folder.mkdirs();
        }
        return folder.getPath();
    }
}
