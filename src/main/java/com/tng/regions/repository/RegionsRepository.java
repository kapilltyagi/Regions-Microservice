package com.tng.regions.repository;

import com.tng.regions.entity.Regions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.data.relational.core.query.Criteria.where;

@RequiredArgsConstructor
@Component
@Slf4j
public class RegionsRepository {
    @Autowired
    private final DatabaseClient databaseClient;

    public Flux<Regions> getAllRegions() {
        log.info("getAllRegions() started");
        Flux<Regions> result;
        result = this.databaseClient.select()
                .from(Regions.class)
                .fetch().all();
        log.info("getAllRegions() completed");
        return result;
    }

    public Mono<Regions> findById(UUID region_id) {
        log.info("findById() started");
        Mono<Regions> result;
        result = this.databaseClient.select().from(Regions.class)
                .matching(where("region_id").is(region_id))
                .fetch().one();
        log.info("findById() completed");
        return result;
    }

    public  Mono<Regions> findByNameContains(String name) {
        log.info("findByNameContains() started");
        Mono<Regions> result;
        result = this.databaseClient.select()
                .from(Regions.class)
                .matching(where("name").like(name))
                .orderBy(Sort.Order.asc("name"))
                .fetch()
                .one();
        log.info("findByNameContains() completed");
        return result;
    }

    public Mono<Integer> update(Regions regions) {
        log.info("update() started");
        Mono<Integer> result;
        result = this.databaseClient.update().table(Regions.class)
                .using(regions)
                .fetch().rowsUpdated();
        log.info("update() completed");
        return result;
    }

    public Mono<Integer> deleteById(UUID region_id) {
        log.info("deleteById() started");
        Mono<Integer> result;
        result = this.databaseClient.delete().from(Regions.class)
                .matching(where("region_id").is(region_id))
                .fetch()
                .rowsUpdated();
        log.info("deleteById() completed");
        return result;
    }

    public Flux<Regions> findAllByParams(String regionCode) {
        log.info("findAllByParams() started");
        Flux<Regions> result;
        result = this.databaseClient.execute("SELECT * " +
                "FROM tng.regions WHERE region_code = (:regionCode) " +
                "ORDER BY display_order, name")
                .bind("regionCode", regionCode)
                .as(Regions.class)
                .fetch()
                .all();
        log.info("findAllByParams() completed");
        return result;
    }

}
