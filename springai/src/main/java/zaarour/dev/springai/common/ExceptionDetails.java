package zaarour.dev.springai.common;

import java.time.LocalDateTime;

public record ExceptionDetails(LocalDateTime timestamp, String message, String details) {
}