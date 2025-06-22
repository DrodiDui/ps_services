alter table workspace_members
    add constraint workspace_members_workspace_member_id_pk
        primary key (workspace_id, member_id);

alter table workspace_members
    add constraint workspace_members_workspace_id_fk
        foreign key (workspace_id) references workspaces (workspace_id);
alter table workspace_members
    add constraint workspace_members_member_id_fk
        foreign key (member_id) references members (member_id);