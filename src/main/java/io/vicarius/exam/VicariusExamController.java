package io.vicarius.exam;


import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpResponseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class VicariusExamController {

    private final ElasticsearchOperations ops;
    private final LogTraceRepo repo;

    @PostMapping("/index/create")
    public ResponseEntity<?> createIndex(@RequestParam(name = "name") String name) {
        try {
            if (!ops.indexOps(IndexCoordinates.of(name)).exists()) {
                if (ops.indexOps(IndexCoordinates.of(name)).create()) {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body("elastic search index: " + name + " was created successfully");
                } else {
                    return ResponseEntity.badRequest()
                            .body("something went wrong. failed to create index: " + name);
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("index name: " + name + " already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/index/delete")
    public ResponseEntity<?> deleteIndex(@RequestParam(name = "name") String name) {
        try {
            if (ops.indexOps(IndexCoordinates.of(name)).exists()) {
                if (ops.indexOps(IndexCoordinates.of(name)).delete()) {
                    return ResponseEntity.status(HttpStatus.OK)
                            .body("elastic search index: " + name + " was deleted successfully");
                } else {
                    return ResponseEntity.badRequest()
                            .body("something went wrong. failed to create index: " + name);
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("index name: " + name + " does not exists");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/doc/log-trace")
    public ResponseEntity<LogTrace> index(@RequestBody LogTrace doc) {
        doc.setTimestamp(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(doc));
    }

    @GetMapping("/doc/log-trace/{id}")
    public LogTrace getById(@PathVariable("id") String id) throws HttpResponseException {
        return repo.findById(id).orElseThrow(() -> new HttpResponseException(HttpStatus.NOT_FOUND.value(), "Document not found"));
    }

    @GetMapping("/doc/log-trace")
    public Page<LogTrace> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "1000") int size) {
        return repo.findAll(PageRequest.of(page, size, Sort.by("timestamp").descending()));
    }
}
