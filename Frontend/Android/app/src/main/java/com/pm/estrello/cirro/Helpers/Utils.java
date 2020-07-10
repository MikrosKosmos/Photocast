package com.pm.estrello.cirro.Helpers;

import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatEditText;

public class Utils {
    /**
     * Method to check whether the edit texts are not empty.
     *
     * @param editTexts: The Edit text to be checked.
     * @return true if none are empty, else false.
     */
    public static boolean isNotEmpty(AppCompatEditText[] editTexts) {
        for (AppCompatEditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                return false;
            }
        }
        return true;
    }
}
