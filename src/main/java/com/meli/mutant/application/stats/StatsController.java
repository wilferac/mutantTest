package com.meli.mutant.application.stats;

import com.meli.mutant.application.Human.HumanService;
import com.meli.mutant.application.Human.domain.Human;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final HumanService humanService;


    @GetMapping("/verification")
    public Flux<Human> isMutant() {
        return humanService.getAll();
    }

}
