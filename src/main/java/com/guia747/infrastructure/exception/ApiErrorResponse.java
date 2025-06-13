package com.guia747.infrastructure.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatusCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final List<ErrorDetail> details;

    private ApiErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.details = builder.details.isEmpty() ? null : List.copyOf(builder.details);
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorDetail> getDetails() {
        return details;
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

    public record ErrorDetail(String field, String message) {

    }
}
