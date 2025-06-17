package com.guia747.cities.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private String url;
    private Integer width;
    private Integer height;

    public static Image createNew(String url, Integer width, Integer height) {
        return new Image(url, width, height);
    }
}
