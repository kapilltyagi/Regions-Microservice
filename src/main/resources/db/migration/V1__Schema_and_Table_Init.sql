create extension if not exists "uuid-ossp";

create schema if not exists tng;

create table tng.regions
(
    region_id uuid     default uuid_generate_v4() primary key,
    name                text                  not null,
    region_code       text,
    country_codes             text[],
    display_order       smallint default 1    not null
);

create index regions_region_code_index
    on tng.regions (region_code);
