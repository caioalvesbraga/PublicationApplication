package libraryProject.restApi.exceptions;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorValidationResponse> methodJdbcException(JdbcSQLIntegrityConstraintViolationException ex) {
        var errorValidationResponse = new ErrorValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Entrada de dados inválida."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidationResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorValidationResponse> methodIllegalArgumentException(IllegalArgumentException ex) {
        var errorValidationResponse = new ErrorValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Entrada de dados inválida."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidationResponse);
    }

    @ExceptionHandler(PaginationException.class)
    public ResponseEntity<ErrorValidationResponse> methodPaginationException(PaginationException ex) {
        var errorValidationResponse = new ErrorValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "As páginas se iniciam em 0 (zero) e o número mínimo de elementos por página é 5."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidationResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorValidationResponse> methodResourceNotFoundException(ResourceNotFoundException ex) {
        var errorValidationResponse = new ErrorValidationResponse(
                HttpStatus.NOT_FOUND.value(),
                "Nenhuma publicação encontrada."
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorValidationResponse);
    }

    @ExceptionHandler(PublicationAlreadyRegisteredException.class)
    public ResponseEntity<ErrorValidationResponse> methodPublicationAlreadyRegisteredException(PublicationAlreadyRegisteredException ex) {
        var errorValidationResponse = new ErrorValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Uma publicação com esse ISBN já foi registrada."
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorValidationResponse);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorValidationResponse> methodRuntimeException(RuntimeException ex) {
//        var errorValidationResponse = new ErrorValidationResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Erro imprevisto de sistema. Contactar administrador.");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorValidationResponse);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidationField>> methodArgumentException(MethodArgumentNotValidException ex) {
        List<ErrorValidationField> errorValidationFields = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ErrorValidationField(e.getField(), e.getDefaultMessage()))
                .toList();
        return new ResponseEntity<>(errorValidationFields, HttpStatus.BAD_REQUEST);
    }
}
