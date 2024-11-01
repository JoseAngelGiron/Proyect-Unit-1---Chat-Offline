package com.github.JoseAngelGiron.persistance;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String v) {
        return (v != null ? LocalDateTime.parse(v, FORMATTER) : null);
    }

    @Override
    public String marshal(LocalDateTime v) {
        return (v != null ? v.format(FORMATTER) : null);
    }
}


