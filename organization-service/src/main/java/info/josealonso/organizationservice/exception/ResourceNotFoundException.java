package info.josealonso.organizationservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceNotFoundException extends Throwable {
    private final String message;
}
