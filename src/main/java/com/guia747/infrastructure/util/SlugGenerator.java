package com.guia747.infrastructure.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class SlugGenerator {

    private SlugGenerator() {
    }

    public static String generateSlug(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        String slug = text.toLowerCase();
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        slug = pattern.matcher(slug).replaceAll("");
        slug = slug.replaceAll("[^\\p{Alnum}]+", "-");
        slug = slug.replaceAll("-+", "-");
        slug = slug.replaceAll("^-|-$", "");

        return slug;
    }
}
