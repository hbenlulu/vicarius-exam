package io.vicarius.exam;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@Builder
@Document(indexName = "log-trace")
public class LogTrace {

    @Id
    private String id;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    private String message;

    private String clientId;

    private String deviceType;

    private String os;
}
