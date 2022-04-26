package com.conpds.ocrenginedemo.java.ocrenginedemoapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.codevog.android.license_library.MainInteractor;
import com.codevog.android.license_library.MainInteractorImpl;
import com.codevog.android.license_library.client_side_exception.BaseOcrException;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class OcrModule extends ReactContextBaseJavaModule {

    OcrModule(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "OcrModule";
    }

    @ReactMethod
    public void getOcrDatafromImage(String base64, String fileName, double fileSize, String type, String uri, Callback callBack) {
        Log.d("RN Request : ", uri);

        String API_KEY = "YOUR_ANDROID_API_KEY";
        String LICENSE_KEY = "YOUR_ANDROID_LICENSE_KEY";
        MainInteractorImpl ocrEngine = new MainInteractorImpl(getCurrentActivity());
        String request = ocrEngine.generateLicenseRequest(API_KEY, LICENSE_KEY);
        Log.d("OCR Request : ", request);
        ocrEngine.recogFromFileToJson(
                API_KEY,
                LICENSE_KEY,
                new String[]{uri},
                new MainInteractor.Callback() {
                    @Override
                    public void recogOk(Map<String, String> map) {
                        callBack.invoke(map.toString());
                    }

                    @Override
                    public void recogError(BaseOcrException e) {
                        callBack.invoke(e.getMessage());
                    }
                }
        );
    }

    public static JSONObject convertMapToJson(ReadableMap readableMap)
            throws JSONException {

        JSONObject object = new JSONObject();

        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();

        while (iterator.hasNextKey()) {

            String key = iterator.nextKey();

            switch (readableMap.getType(key)) {

                case Null:

                    object.put(key, JSONObject.NULL);

                    break;

                case Boolean:

                    object.put(key, readableMap.getBoolean(key));

                    break;

                case Number:

                    object.put(key, readableMap.getDouble(key));

                    break;

                case String:

                    object.put(key, readableMap.getString(key));

                    break;

                case Map:

                    object.put(key,convertMapToJson(readableMap.getMap(key)));

                    break;

//                case Array:
//
//                    object.put(key, convertArrayToJson(readableMap.getArray( key)));
//
//                    break;
            }

        }

        return object;

    }
}
