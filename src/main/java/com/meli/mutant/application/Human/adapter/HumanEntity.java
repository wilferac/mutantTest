package com.meli.mutant.application.Human.adapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("human")
public class HumanEntity {
    @Id
    @Column("id")
    private Integer id;

    @Column("dna")
    private String dna;

    @Column("isMutant")
    private Boolean isMutant;

}
