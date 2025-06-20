package com.guia747.web.dtos;

import com.guia747.domain.city.valueobject.Image;

public record ImageResponse(
        String url,
        int width,
        int height
) {

    public static ImageResponse from(Image image) {
        return new ImageResponse(
                image.getUrl(),
                image.getWidth(),
                image.getHeight()
        );
    }
}
