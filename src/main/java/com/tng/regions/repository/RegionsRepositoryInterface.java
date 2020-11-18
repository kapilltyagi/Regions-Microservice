package com.tng.regions.repository;

import com.tng.regions.entity.Regions;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface RegionsRepositoryInterface extends ReactiveCrudRepository<Regions, UUID> {
}
