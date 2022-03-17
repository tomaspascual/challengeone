package com.wabi.challengeone.component.io;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.wabi.challengeone.dto.FilmDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.stream.Stream;

@Component
public class IOComponent {

    @Value("${challengeone.csv.path}")
    private String csvPath;

    private final static Logger logger = LoggerFactory.getLogger(IOComponent.class);


    private ClassPathResource getExampleResource() {
        return new ClassPathResource("deniro.csv");
    }

    private Stream<FilmDTO> readCsvStream() throws IOException {

        FileSystemResource resource = new FileSystemResource(csvPath);
        Reader r = new FileReader(resource.getFile());
        CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader()
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setTrim(true)
                .setQuote('"')
                .build();
        Iterable<CSVRecord> records = format.parse(r);

        Stream.Builder<FilmDTO> sBuilder = Stream.builder();

        for (CSVRecord record : records) {
            sBuilder.add(
                FilmDTO.builder()
                        .Year(Integer.parseInt(record.get("Year")))
                        .Score(Integer.parseInt(record.get("Score")))
                        .Title(record.get("Title"))
                        .build()
            );
        }

        return sBuilder.build();
    }


    public String createJsonString() throws IOException {
        JsonFactory factory = new JsonFactory();
        StringWriter jsonObjectWriter = new StringWriter();
        JsonGenerator generator = factory.createGenerator(jsonObjectWriter);

        generator.writeStartArray();
        readCsvStream()
                .forEach(filmDTO -> {
                    logger.info(filmDTO.toString());
                    try {
                        generator.writeStartObject();
                        generator.writeNumberField("year", filmDTO.getYear());
                        generator.writeNumberField("score", filmDTO.getScore());
                        generator.writeStringField("title", filmDTO.getTitle().replaceAll("\"", ""));
                        generator.writeEndObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        generator.writeEndArray();
        generator.close();
        return jsonObjectWriter.toString();
    }


}
