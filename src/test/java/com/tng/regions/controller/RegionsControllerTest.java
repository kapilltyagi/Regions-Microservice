package com.tng.regions.controller;

import com.tng.regions.entity.Regions;
import com.tng.regions.repository.RegionsRepositoryInterface;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
public class RegionsControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RegionsRepositoryInterface regionsRepositoryInterface;

    /*@BeforeAll
    public void setUp(){
        Regions region = new Regions(null,"name-1","usa",Arrays.asList("CC-1","CC-2","CC-3"), (short) 1) ;
        regionsRepositoryInterface.save(region)
                .doOnNext(item->System.out.println("Inserted Item is : "+item))
                .block();
    }*/


    @Test
    public void getAllRegionsTest(){
        webTestClient.get().uri("/admin/regions")
                //.accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Regions.class)
                .toString();


    }
    @Test
    public void getRegionByIdTest(){
        webTestClient.get().uri("/admin/regions/{region_id}","0c16a85c-179a-4696-b2e5-e4b854f7beb9")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.name","name-1");
    }
    @Test
    public void createRegionsTest(){
        Regions item = new Regions(UUID.fromString("0c16a85c-179a-4696-b2e5-e4b854f7beb9"),"name-2","usa",Arrays.asList("CC-12","CC-22","CC-32"), (short) 1);
        webTestClient.post().uri("/admin/regions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(item),Regions.class)
                //.exchange()
                //.expectStatus()
                .toString();
                //.expectBody()
                //.jsonPath("$.regionId").isNotEmpty()
                //.jsonPath("$.name","name-2");
    }
    @Test
    public void updateRegionTest(){
        short displayOrder = 2;
        Regions item = new Regions(null,"name-1","usa",Arrays.asList("CC-1","CC-2","CC-3"),displayOrder );
        webTestClient.put().uri("/admin/regions/{region_id}","0c16a85c-179a-4696-b2e5-e4b854f7beb9")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(item),Regions.class)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
               // .jsonPath("$.regionId").isNotEmpty()
                .jsonPath("$.name","name-1");
    }

    @Test
    public void deleteRegion(){
        webTestClient.delete().uri("/admin/regions/{region_id}","0c16a85c-179a-4696-b2e5-e4b854f7beb9")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class);
    }
}
