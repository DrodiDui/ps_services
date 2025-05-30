create table spaces
(
    space_id      bigserial         not null,
    title         character varying not null,
    description   character varying not null,
    created_time  timestamptz       not null,
    created_by    bigint            not null,
    modified_time timestamptz       not null,
    modified_by   bigint            not null
) with (autovacuum_enabled = true);


alter table spaces
    add constraint spaces_space_id_kp primary key (space_id);

alter table spaces add constraint space_space_title_uq unique (title);