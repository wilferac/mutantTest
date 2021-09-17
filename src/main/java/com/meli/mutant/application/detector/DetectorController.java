package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.Human;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
@RequestMapping("/detector")
public class DetectorController {
    private final DetectorService detectorService;

    @PostMapping("/mutant")
//    @Operation(summary = "Validate if a human is a mutant")
    public Mono<ResponseEntity> isMutant(@RequestBody Human human) {
        return detectorService.isMutant(human);
    }

}
