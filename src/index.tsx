import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-get-intent' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const ReactNativeGetIntent = NativeModules.ReactNativeGetIntent
  ? NativeModules.ReactNativeGetIntent
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

/**
 * Intent interface that is analogous to Android's Intent class.
 *
 * @see https://developer.android.com/reference/android/content/Intent
 */
export interface Intent {
  action: string;
  data: string;
  categories: string[];
  extras: { [k: string]: unknown };
}

/**
 * Gets the current Android activity's intent.
 *
 * @see https://developer.android.com/reference/android/app/Activity#getIntent()
 */
export function getIntent(): Promise<Intent> {
  return ReactNativeGetIntent.getIntent();
}
