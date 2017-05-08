package by.bsu.zakharankou.restservices.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Used to serialize {@link Date}, which is not a common JSON
 * type, so we have to create a custom serialize method.
 * Date format: "dd-MM-yyyy hh:mm:ss a"
 * Time zone: UTC
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    public static final String DATE_PATTERN = "MM-dd-yyyy hh:mm:ss a";

    public static final String TIMEZONE_UTC = "UTC";

    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));

        String formattedDate = dateFormat.format(date);
        generator.writeString(formattedDate);
    }
}