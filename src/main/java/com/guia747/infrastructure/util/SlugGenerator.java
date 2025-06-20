package com.guia747.infrastructure.util;

import java.text.Normalizer;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class SlugGenerator {

    private SlugGenerator() {
    }

    public static String generateSlug(String text, Function<String, Boolean> exists) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        String baseSlug = toSlug(text);
        String candidate = baseSlug;
        int counter = 1;

        while (exists.apply(candidate)) {
            candidate = baseSlug + "-" + counter++;
        }

        return candidate;
    }

    public static String toSlug(String text) {
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
