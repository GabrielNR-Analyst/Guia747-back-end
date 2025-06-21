package com.guia747.web.dtos.user;

import java.util.UUID;
import com.guia747.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing user account details after successful authentication")
public record UserDetailsResponse(
        @Schema(description = "Unique identifier of the user account", format = "uuid")
        UUID accountId,
        @Schema(description = "The email address of the user", format = "email")
        String email,
        @Schema(description = "The full name of the user")
        String name,
        @Schema(description = "URL to the user's profile picture")
        String profilePictureUrl
) {

    public static UserDetailsResponse from(User user) {
        return new UserDetailsResponse(user.getId(), user.getEmail(), user.getName(),
                user.getProfilePictureUrl());
    }
}
