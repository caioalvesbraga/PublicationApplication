package libraryProject.restApi.exceptions;

public record ErrorValidationResponse(int httpStatusCode, String errorMessage) {
}
