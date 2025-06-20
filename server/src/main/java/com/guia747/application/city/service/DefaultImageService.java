package com.guia747.application.city.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import com.guia747.domain.city.valueobject.Image;

@Service
public class DefaultImageService implements ImageService {

    @Override
    public Image resolve(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return null;
        }

        try {
            URI uri = URI.create(imageUrl);
            BufferedImage image = ImageIO.read(uri.toURL());
            if (image != null) {
                return Image.createNew(imageUrl, image.getWidth(), image.getHeight());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
