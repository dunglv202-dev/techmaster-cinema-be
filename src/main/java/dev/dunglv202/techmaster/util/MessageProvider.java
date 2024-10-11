package dev.dunglv202.techmaster.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProvider {
    private final MessageSource messageSource;

    public String getLocalizedMessage(String code) {
        return getLocalizedMessage(code, LocaleContextHolder.getLocale());
    }

    public String getLocalizedMessage(String code, Locale locale) {
        try {
            return messageSource.getMessage(
                code,
                null,
                locale
            );
        } catch (NoSuchMessageException e) {
            log.warn("No message found for {{}}", code);
            return code;
        }
    }
}
