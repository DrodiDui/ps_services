create table reference_types
(
    reference_type character varying not null,
    description    jsonb             not null
) with (autovacuum_enabled = true);

alter table reference_types
    add constraint reference_types_reference_type_pk primary key (reference_type);


create table reference_items
(
    reference_item_id bigserial         not null,
    reference_type    character varying not null,
    item_code         character varying not null,
    description       jsonb             not null,
    is_active         boolean           not null
) with (autovacuum_enabled = true);

alter table reference_items
    add constraint reference_items_reference_item_id_pk primary key (reference_item_id);

alter table reference_items
    add constraint reference_items_reference_item_code_uq
        unique (reference_type, item_code);

create table reference_metadata
(
    metadata_id       bigserial not null,
    reference_item_id bigint    not null,
    workspace_id      bigint,
    metadata          jsonb     not null
) with (autovacuum_enabled = true);

alter table reference_metadata
    add constraint reference_metadata_metadata_id_pk primary key (metadata_id);

alter table reference_metadata
    add constraint reference_metadata_reference_item_id_fk foreign key (reference_item_id) references reference_items (reference_item_id);