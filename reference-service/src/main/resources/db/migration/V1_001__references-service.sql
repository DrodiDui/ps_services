create table references_type
(
    reference_type character varying not null,
    description    jsonb             not null
) with (autovacuum_enabled = true);

alter table references_type
    add constraint references_type_reference_type_pk primary key (reference_type);

create table reference_item
(
    reference_item_id bigserial         not null,
    reference_type    character varying not null,
    item_code         character varying not null,
    description       jsonb             not null
) with (autovacuum_enabled = true);

alter table reference_item
    add constraint reference_item_reference_item_id_pk primary key (reference_item_id);

alter table reference_item
    add constraint reference_item_reference_type_fk foreign key (reference_type)
        references references_type (reference_type);


alter table reference_item
    add constraint reference_item_reference_type_item_code_uq
        unique (reference_type, item_code);