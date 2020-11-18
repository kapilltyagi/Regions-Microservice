package com.tng.regions.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tng.regions")
public class Regions {

    @Id
    @Column("region_id")
    private UUID regionId;

    private String name;

    @Column("region_code")
    private String regionCode;

    @Column("country_codes")
    private List<String> countryCodes = new ArrayList<>();

    @Column("display_order")
    private short displayOrder;
}
