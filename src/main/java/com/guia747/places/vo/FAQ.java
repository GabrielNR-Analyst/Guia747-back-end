package com.guia747.places.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FAQ {

    private String question;
    private String answer;

    public static FAQ createNew(String question, String answer) {
        return new FAQ(question, answer);
    }
}
