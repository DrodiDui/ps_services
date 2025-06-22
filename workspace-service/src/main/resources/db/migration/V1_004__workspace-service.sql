alter table workspaces add column is_deleted boolean not null default false;
alter table projects add column is_deleted boolean not null default false;
alter table members add column is_deleted boolean not null default false;