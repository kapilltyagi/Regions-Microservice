package com.tng.regions.controller;

import com.tng.regions.entity.Regions;
import com.tng.regions.exception.InvalidFieldException;
import com.tng.regions.repository.RegionsRepository;
import com.tng.regions.repository.RegionsRepositoryInterface;
import com.tng.regions.service.HttpRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Slf4j
@Valid
@RequestMapping("/admin/regions")
public class RegionsController {

    @Autowired
    private HttpRequestInterceptor interceptor;

    @Autowired
    private RegionsRepository regionsRepository;
    @Autowired
    private RegionsRepositoryInterface regionsRepositoryInterface;

    @GetMapping()
    public Flux<Regions> getAllRegions(){

        return regionsRepository.getAllRegions();
    }

    @GetMapping("/{region_id}")
    public Mono<ResponseEntity<Regions>> getRegionById(@PathVariable("region_id") @Min(36) UUID region_id){
        log.info("getRegionById() started");
        Mono<ResponseEntity<Regions>> result;
        try {
            result = regionsRepository.findById(region_id)
                    .map(obj -> new ResponseEntity<>(obj, HttpStatus.OK))
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            log.info("getRegionById() completed");
            return result;
        }catch(Exception exception){
            log.error("Exception occured in getRegionById()");
            throw new InvalidFieldException("Region Id is not valid");
        }

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Regions>> createRegions(@RequestBody Regions regions) {
        log.info("createRegions() started");
        Mono<ResponseEntity<Regions>> result;
        if(regions.getName()==null){
            throw new InvalidFieldException("Name field should not be null",HttpStatus.FAILED_DEPENDENCY);
        }
        result = regionsRepository.findByNameContains(regions.getName()!=null?regions.getName():"")
                .switchIfEmpty(regionsRepositoryInterface.save(regions))
                .map((s) -> new ResponseEntity<>(s, HttpStatus.CREATED));

        log.info("createRegions() completed");
        return result;
    }

    @PutMapping("/{region_id}")
    public Mono<ResponseEntity<Integer>> updateRegion(@PathVariable UUID region_id, @RequestBody Regions regions){
        log.info("updateRegion() started");
        Mono<ResponseEntity<Integer>> result;
        result = regionsRepository.findById(region_id)
                .map(data -> {
                    new ModelMapper().map(regions,data);
                    data.setRegionId(region_id);
                    return data;
                })
                .flatMap(data-> regionsRepository.update(data))
                .map(data -> new ResponseEntity<>(data,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        log.info("updateRegion() completed");
        return result;
    }

    @DeleteMapping("/{region_id}")
    public Mono<Integer> deleteRegion(@PathVariable UUID region_id) {
        log.info("deleteRegion() started");
        Mono<Integer> result;
        result = regionsRepository.deleteById(region_id);
        log.info("deleteRegion() completed");
        return result;
    }

    @PatchMapping("/{region_id}")
    public Mono<ResponseEntity<Integer>> patch(@PathVariable UUID region_id, @RequestBody Map<Object,Object> fields){
        log.info("patch() started");
        Mono<ResponseEntity<Integer>> result;
        result = regionsRepository.findById(region_id)
                .map(data-> {
                    fields.forEach((k,v) -> {
                        Field field = ReflectionUtils.findField(Regions.class,(String) k);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field,data,v);
                    });
                    return data;
                })
                .flatMap(data-> regionsRepository.update(data))
                .map(data -> new ResponseEntity<>(data,HttpStatus.OK));
        log.info("patch() completed");
        return result;
    }

    @GetMapping("/headers")
    public Flux<Regions> allRegionsByHeader() {
        log.info("allRegionsByHeader() Started");
        Flux<Regions> result;
        result = regionsRepository.findAllByParams(
                interceptor.getHeaderValueByKey("x-tng-region")
        );
        log.info("allRegionsByHeader() completed");
        return result;
    }


}
