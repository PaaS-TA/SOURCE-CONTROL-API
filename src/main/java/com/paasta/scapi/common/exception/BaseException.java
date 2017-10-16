package com.paasta.scapi.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Created by ijlee on 2017-07-12.
 */
@Getter
@Setter
public class BaseException extends Exception {
    protected static final Logger logger = LoggerFactory.getLogger(BaseException.class);

    private static final long serialVersionUID = 1L;

    protected String message;
    protected String messageKey;
    protected Object[] messageParameters;
    protected Exception wrappedException;

    public BaseException() {
        this("BaseException without message", null, null);
    }

    /**
     * BaseException 생성자
     * @param defaultMessage 메세지 지정
     */
    public BaseException(String defaultMessage) {
        this(defaultMessage, null, null);
    }
    /**
     * BaseException 생성자
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(Throwable wrappedException) {
        this("BaseException without message", null, wrappedException);
    }

    /**
     * BaseException 생성자
     * @param defaultMessage 메세지 지정
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(String defaultMessage, Throwable wrappedException) {
        this(defaultMessage, null, wrappedException);
    }

    /**
     * BaseException 생성자
     * @param defaultMessage 메세지 지정(변수지정)
     * @param messageParameters 치환될 메세지 리스트
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(String defaultMessage, Object[] messageParameters, Throwable wrappedException) {
        super(wrappedException);

        String userMessage = defaultMessage;
        if (messageParameters != null) {
            userMessage = MessageFormat.format(defaultMessage, messageParameters);
        }
        message = userMessage;

    }

    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     */
    public BaseException(MessageSource messageSource, String messageKey) {
        this(messageSource, messageKey, null, null, Locale.getDefault(), null);
    }

    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     */
    public BaseException(MessageSource messageSource, String messageKey, Throwable wrappedException) {
        this(messageSource, messageKey, null, null, Locale.getDefault(), wrappedException);
    }
    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     * @param locale 국가/언어지정
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(MessageSource messageSource, String messageKey, Locale locale, Throwable wrappedException) {
        this(messageSource, messageKey, null, null, locale, wrappedException);
    }
    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     * @param messageParameters 치환될 메세지 리스트
     * @param locale 국가/언어지정
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters, Locale locale,
                         Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, null, locale, wrappedException);
    }
    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     * @param messageParameters 치환될 메세지 리스트
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters,
                         Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, null, Locale.getDefault(), wrappedException);
    }
    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     * @param messageParameters 치환될 메세지 리스트
     * @param defaultMessage 메세지 지정(변수지정)
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters,
                         String defaultMessage, Throwable wrappedException) {
        this(messageSource, messageKey, messageParameters, defaultMessage, Locale.getDefault(), wrappedException);
    }
    /**
     * BaseException 생성자
     * @param messageSource 메세지 리소스
     * @param messageKey 메세지키값
     * @param messageParameters 치환될 메세지 리스트
     * @param defaultMessage 메세지 지정(변수지정)
     * @param locale 국가/언어지정
     * @param wrappedException 발생한 Exception 내포함.
     */
    public BaseException(MessageSource messageSource, String messageKey, Object[] messageParameters,
                         String defaultMessage, Locale locale, Throwable wrappedException) {
        super(wrappedException);

        this.messageKey = messageKey;
        this.messageParameters = messageParameters;
        message = messageSource.getMessage(messageKey, messageParameters, defaultMessage, locale);

    }

}
