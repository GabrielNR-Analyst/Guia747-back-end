package com.guia747.accounts;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserAccountDetailsResponse(UUID accountId, String email, String name, String profilePictureUrl,
        LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static UserAccountDetailsResponse fromUserAccount(UserAccount userAccount) {
        return new UserAccountDetailsResponse(userAccount.getId(), userAccount.getEmail(), userAccount.getName(),
                userAccount.getProfilePictureUrl(), userAccount.getCreatedAt(), userAccount.getUpdatedAt());
    }
}
