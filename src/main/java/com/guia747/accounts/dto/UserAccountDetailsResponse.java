package com.guia747.accounts.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.guia747.accounts.domain.UserAccount;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing user account details after successful authentication")
public record UserAccountDetailsResponse(
        @Schema(description = "Unique identifier of the user account", format = "uuid")
        UUID accountId,
        @Schema(description = "The email address of the user", format = "email")
        String email,
        @Schema(description = "The full name of the user")
        String name,
        @Schema(description = "URL to the user's profile picture")
        String profilePictureUrl,
        @Schema(description = "Timestamp when the user account was created")
        LocalDateTime createdAt,
        @Schema(description = "Timestamp when the user account was last updated")
        LocalDateTime updatedAt) {

    public static UserAccountDetailsResponse fromUserAccount(UserAccount userAccount) {
        return new UserAccountDetailsResponse(userAccount.getId(), userAccount.getEmail(), userAccount.getName(),
                userAccount.getProfilePictureUrl(), userAccount.getCreatedAt(), userAccount.getUpdatedAt());
    }
}
