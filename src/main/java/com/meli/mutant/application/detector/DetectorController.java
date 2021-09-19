package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.domain.DnaChain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Validar si una cadena de adn pertenece a un mutante",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cadenas de ADN con un valor minimo 4x4 y maximo de 20x20", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "el adn es mutante", content = @Content),
            @ApiResponse(responseCode = "400", description = "parametros de entrada invalidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "el adn es humano", content = @Content)})
    @PostMapping("/mutant")
    public Mono<ResponseEntity> isMutant(@RequestBody DnaChain dnaChain) {
        return detectorService.isMutant(dnaChain);
    }

}
