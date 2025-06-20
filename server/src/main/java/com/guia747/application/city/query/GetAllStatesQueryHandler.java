package com.guia747.application.city.query;

import java.util.List;
import com.guia747.web.dtos.city.StateResponse;

public interface GetAllStatesQueryHandler {

    List<StateResponse> handle();
}
