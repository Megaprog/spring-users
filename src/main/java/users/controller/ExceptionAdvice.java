package users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
class ExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(AppException.class)
    public void dragRacingHandler(HttpServletResponse response, AppException e) throws IOException {
        log.debug("Conventional exception caught", e);

        final int status;
        if (e instanceof UserNotFoundException) {
            status = (HttpStatus.NOT_FOUND.value());
        }
        else {
            status = HttpStatus.NOT_IMPLEMENTED.value();
        }

        response.sendError(status);
    }

    public static class AppException extends RuntimeException {
    }

    public static class UserNotFoundException extends AppException {
    }
}