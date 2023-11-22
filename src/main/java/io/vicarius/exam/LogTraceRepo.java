package io.vicarius.exam;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogTraceRepo extends ElasticsearchRepository<LogTrace, String> {
}
