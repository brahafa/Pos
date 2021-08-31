package com.pos.bringit.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LanguageWrapper extends ContextWrapper {

    public LanguageWrapper(Context context) {
        super(context);
    }

    public static void wrap(Context context, String language) {
        Configuration config = new Configuration(context.getResources().getConfiguration());
        Locale sysLocale;
        sysLocale = getSystemLocale(config);
        if (language != null && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            setSystemLocale(config, locale);

            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    public static Locale getSystemLocale(Configuration configuration) {
        return Build.VERSION.SDK_INT >= 24 ? configuration.getLocales().get(0) : configuration.locale;
    }

    public static void setSystemLocale(Configuration configuration, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24)
            configuration.setLocale(locale);
        else
            configuration.locale = locale;
    }
}