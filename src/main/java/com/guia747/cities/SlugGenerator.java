package com.guia747.cities;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugGenerator {

    public static String generateSlug(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(normalized).replaceAll(""); // Remove accents
        slug = slug.toLowerCase(); // Convert to lowercase
        slug = slug.replaceAll("[^a-z0-9\\s-]", ""); // Remove special characters, except hyphens and spaces
        slug = slug.replaceAll("\\s+", "-"); // Replace spaces with hyphens
        slug = slug.replaceAll("^-|-$", ""); // Remove hyphens at start or end
        return slug;
    }
}
