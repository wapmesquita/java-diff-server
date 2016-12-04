package io.github.wapmesquita.diffweb.resources;

import io.github.wapmesquita.diffweb.enums.DiffOption;

public class UrlPatterns {

    public static final String DIFF = "/diff";
    public static final String DIFF_REQUEST = DIFF + "/%s";
    public static final String DIFF_UPLOAD = DIFF + "/%s/%s";


    public static String getRequestDiffPatter(String id) {
        return String.format(DIFF_REQUEST, id);
    }

    public static String getUploadDiffPattern(String id, DiffOption option) {
        return String.format(DIFF_UPLOAD, id, option.getOption());
    }
}
