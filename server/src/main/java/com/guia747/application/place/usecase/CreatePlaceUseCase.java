package com.guia747.application.place.usecase;

import java.util.UUID;
import com.guia747.web.dtos.place.CreatePlaceRequest;
import com.guia747.web.dtos.place.CreatePlaceResponse;

public interface CreatePlaceUseCase {

    CreatePlaceResponse execute(UUID ownerId, CreatePlaceRequest request);
}
