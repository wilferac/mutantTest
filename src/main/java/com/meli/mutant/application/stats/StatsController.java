package com.meli.mutant.application.stats;

import com.meli.mutant.application.stats.domain.VerificationStats;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/stats")
public class StatsController {
    private final StatsService statsService;

    @Operation(summary = " Obtener estadísticas de las verificaciones de ADN")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "ratio obtenido correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VerificationStats.class)))
    })
    @GetMapping("/verification")
    public Mono<VerificationStats> getVerificationStats() {
        return statsService.getVerificationStats();
    }

}
