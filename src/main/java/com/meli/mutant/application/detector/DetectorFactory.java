package com.meli.mutant.application.detector;

import com.meli.mutant.application.detector.validator.MutantValidator;
import org.springframework.context.annotation.Bean;

public class DetectorFactory {

    @Bean
    public MutantValidator mutantCheckService() {
        return new MutantValidator();
    }

    @Bean
    public DetectorService mutantCheckService(MutantValidator mutantValidator) {
        return new DetectorService(mutantValidator);
    }


}
