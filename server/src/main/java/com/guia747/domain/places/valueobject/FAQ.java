package com.guia747.domain.places.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class FAQ {

    private String question;
    private String answer;

    public static FAQ createNew(String question, String answer) {
        return new FAQ(question, answer);
    }
}
