/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.dialer.calllog;

import android.content.Context;
import android.content.res.Resources;
import android.provider.CallLog.Calls;
import android.telecom.PhoneAccountHandle;
import android.text.TextUtils;
import android.util.Log;

import com.android.dialer.R;

/**
 * Helper for formatting and managing the display of phone numbers.
 */
public class PhoneNumberDisplayUtil {

    /**
     * Returns the string to display for the given phone number if there is no matching contact.
     */
    /* package */ static CharSequence getDisplayName(
            Context context,
            PhoneAccountHandle accountHandle,
            CharSequence number,
            int presentation,
            boolean isVoicemail) {
        if (presentation == Calls.PRESENTATION_UNKNOWN) {
            return context.getResources().getString(R.string.unknown);
        }
        if (presentation == Calls.PRESENTATION_RESTRICTED) {
            return context.getResources().getString(R.string.private_num);
        }
        if (presentation == Calls.PRESENTATION_PAYPHONE) {
            return context.getResources().getString(R.string.payphone);
        }
        if (isVoicemail) {
            return context.getResources().getString(R.string.voicemail);
        }
        if (PhoneNumberUtilsWrapper.isLegacyUnknownNumbers(number)) {
            return context.getResources().getString(R.string.unknown);
        }
        return "";
    }

    /**
     * Returns the string to display for the given phone number.
     *
     * @param accountHandle The handle for the account corresponding to the call
     * @param number the number to display
     * @param formattedNumber the formatted number if available, may be null
     */
    public static CharSequence getDisplayNumber(
            Context context,
            PhoneAccountHandle accountHandle,
            CharSequence number,
            int presentation,
            CharSequence formattedNumber,
            boolean isVoicemail) {
        final CharSequence displayName =
                getDisplayName(context, accountHandle, number, presentation, isVoicemail);
        if (!TextUtils.isEmpty(displayName)) {
            return displayName;
        }

        if (!TextUtils.isEmpty(formattedNumber)) {
            return formattedNumber;
        } else if (!TextUtils.isEmpty(number)) {
            return number;
        } else {
            return "";
        }
    }
}
