package com.guia747.places.dto;

import com.guia747.places.vo.FAQ;

public record FAQData(
        String question,
        String answer
) {

    public static FAQData from(FAQ faq) {
        return new FAQData(faq.getQuestion(), faq.getAnswer());
    }
}
