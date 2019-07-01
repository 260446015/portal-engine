package com.yonyougov.portal.engine;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/26
 * @Description
 */
public class ThemeOccupiedException extends RuntimeException {
    private static final long serialVersionUID = 6468926207526674425L;

    public ThemeOccupiedException() {
        super();
    }

    public ThemeOccupiedException(String message) {
        super(message);
    }

    public ThemeOccupiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThemeOccupiedException(Throwable cause) {
        super(cause);
    }

    protected ThemeOccupiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
