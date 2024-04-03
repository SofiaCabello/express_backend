create table package
(
    id               int                                                                                                 not null
        primary key,
    sign_date        timestamp                                                                                           null,
    shipment_id      int                                                                                                 not null,
    status           enum ('pending', 'processing', 'in_transit', 'delivering', 'signed', 'cancelled') default 'pending' not null,
    batch_id         int                                                                                                 null,
    receiver_id      int                                                                                                 null,
    receiver_name    varchar(50)                                                                                         null,
    receiver_address varchar(255)                                                                                        null,
    receiver_phone   varchar(20)                                                                                         null,
    weight           double                                                                                              null,
    size             varchar(50)                                                                                         null,
    constraint package_ibfk_1
        foreign key (shipment_id) references express.shipment (id),
    constraint package_ibfk_2
        foreign key (batch_id) references express.batch (id),
    constraint packages_customers_id_fk
        foreign key (receiver_id) references express.customer (id)
);

create index shipment_id
    on package (shipment_id);

create index trans_id
    on package (batch_id);


