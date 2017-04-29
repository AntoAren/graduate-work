package by.bsu.zakharankou.restservices.controller.heartbeat;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.HttpStatus.OK;

/**
 * Controller to check services availability.
 *
 */
@Controller
@RequestMapping("/heartbeat")
public class HeartbeatController {

    protected static final String NO_CACHE = "no-cache";

    /**
     * Helps to check whether the system is alive. Body contains current system time in milliseconds.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Long> heartbeat() {
        return new ResponseEntity<>(System.currentTimeMillis(), createResponseHeaders(), OK);
    }

    @RequestMapping(value = "", method = RequestMethod.HEAD)
    public ResponseEntity heartbeatWithoutBody() {
        return new ResponseEntity(createResponseHeaders(), OK);
    }

    private HttpHeaders createResponseHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl(NO_CACHE);

        return httpHeaders;
    }
}
