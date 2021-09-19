package com.meli.mutant.application.Human;

import com.meli.mutant.application.Human.domain.Human;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/human")
public class HumanController {
    private final HumanService humanService;

    @GetMapping("/getAll")
    public Flux<Human> statsVerification() {
        return humanService.getAll();
    }

}
