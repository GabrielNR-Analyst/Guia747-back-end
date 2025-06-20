package com.guia747.domain.city.valueobject;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Image implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String url;
    private Integer width;
    private Integer height;

    public static Image createNew(String url, Integer width, Integer height) {
        return new Image(url, width, height);
    }
}
