package gov.samhsa.c2s.provideruiapi.service.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class UserInfoHelper {

    public static Locale getSelectedLocale() {
        if (LocaleContextHolder.getLocale().getLanguage().isEmpty()) {
            return Locale.US;
        } else {
            return LocaleContextHolder.getLocale();
        }
    }
}
