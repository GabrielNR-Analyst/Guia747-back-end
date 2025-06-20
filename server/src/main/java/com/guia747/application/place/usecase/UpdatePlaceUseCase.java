package com.guia747.application.place.usecase;

import java.util.UUID;
import com.guia747.web.dtos.place.UpdatePlaceRequest;

public interface UpdatePlaceUseCase {

    void execute(UUID placeId, UpdatePlaceRequest request);
}
