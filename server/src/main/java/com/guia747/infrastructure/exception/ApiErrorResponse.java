package com.guia747.infrastructure.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standardized error response format for API errors")
public final class ApiErrorResponse {

    @Schema(description = "HTTP status code of the error", example = "400")
    private final int status;

    @Schema(description = "Unique error code", example = "invalid_request")
    private final String error;

    @Schema(description = "Human-readable message describing the error", example = "Invalid request")
    private final String message;

    @Schema(description = "List of specific error details, typically for validation errors")
    private final List<ErrorDetail> details;

    private ApiErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.details = builder.details.isEmpty() ? null : List.copyOf(builder.details);
    }

    // Entry points
    public static ErrorStage status(int status) {
        return new Builder(status);
    }

    public static ErrorStage status(HttpStatusCode statusCode) {
        return new Builder(statusCode.value());
    }

    // Stage interfaces
    public interface ErrorStage {

        MessageStage error(String error);
    }

    public interface MessageStage {

        BuildStage message(String message);
    }

    public interface BuildStage {

        BuildStage details(Consumer<DetailBuilder> consumer);

        ApiErrorResponse build();
    }

    public interface DetailBuilder {

        DetailBuilder add(String field, String message);
    }

    // Builder implementation
    private static class Builder implements ErrorStage, MessageStage, BuildStage, DetailBuilder {

        private final int status;
        private String error;
        private String message;
        private final List<ErrorDetail> details = new ArrayList<>();

        private Builder(int status) {
            this.status = status;
        }

        @Override
        public MessageStage error(String error) {
            this.error = error;
            return this;
        }

        @Override
        public BuildStage message(String message) {
            this.message = message;
            return this;
        }

        @Override
        public BuildStage details(Consumer<DetailBuilder> consumer) {
            consumer.accept(this);
            return this;
        }

        @Override
        public DetailBuilder add(String field, String message) {
            this.details.add(new ErrorDetail(field, message));
            return this;
        }

        @Override
        public ApiErrorResponse build() {
            return new ApiErrorResponse(this);
        }
    }

    @Schema(description = "Details of a specific error, typically for validation issues")
    public record ErrorDetail(
            @Schema(description = "Name of the field that caused the error", example = "email")
            String field,
            @Schema(description = "A specific message for the error on the field", example = "Invalid email format")
            String message
    ) {

    }
}
