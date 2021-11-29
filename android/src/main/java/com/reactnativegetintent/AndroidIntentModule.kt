package com.reactnativegetintent

import android.os.Bundle
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class ReactNativeGetIntentModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "ReactNativeGetIntent"
    }

    @ReactMethod
    fun getIntent(promise: Promise) {
      val activity = getCurrentActivity()
      if (activity == null) {
        promise.reject(IllegalStateException("getCurrentActivity() returned null"))
        return
      }

      val intent = activity.getIntent()

      // https://reactnative.dev/docs/native-modules-android#argument-types
      val jsIntent = Arguments.createMap()
      jsIntent.putString("action", intent.getAction())
      jsIntent.putString("data", intent.getData().toString())
      jsIntent.putArray("categories", Arguments.fromList(intent.getCategories()?.toList() ?: emptyList<String>()))
      jsIntent.putMap("extras", Arguments.fromBundle(intent.getExtras() ?: Bundle.EMPTY))
      promise.resolve(jsIntent)
    }
}
