create table batch
(
    id          bigint                                 not null
        primary key,
    create_date timestamp default CURRENT_TIMESTAMP null,
    origin      bigint                                 not null,
    destination bigint                                 not null,
    responsible bigint                                 not null,
    status      enum ('in_trans', 'arrive')         not null,
    vehicle_id  bigint                                 null,
    constraint batch_ibfk_1
        foreign key (origin) references express.logistic (id),
    constraint batch_ibfk_2
        foreign key (destination) references express.logistic (id),
    constraint batch_ibfk_3
        foreign key (responsible) references express.employee (id),
    constraint batch_ibfk_4
        foreign key (vehicle_id) references express.vehicle (id)
);

create index destination
    on batch (destination);

create index origin
    on batch (origin);

create index responsible
    on batch (responsible);

create index vehicle_id
    on batch (vehicle_id);

create table customer
(
    id    bigint    not null
        primary key,
    username      varchar(255) null,
    phone         varchar(20)  null,
    password_hash varchar(255) not null,
    address       json         null,
    email         varchar(255) null,
    constraint phone
        unique (phone),
    constraint username
        unique (username)
);

create table employee
(
    id            bigint auto_increment
        primary key,
    name          varchar(255) not null,
    phone         varchar(20)  null,
    password_hash varchar(255) not null,
    salt          varchar(255) not null,
    serve_at      bigint          null,
    email         varchar(255) null,
    constraint phone
        unique (phone),
    constraint employee_ibfk_1
        foreign key (serve_at) references express.logistic (id)
);

create index serve_at
    on employee (serve_at);

create table location
(
    id         bigint       not null
        primary key,
    coordinate point     null,
    time       timestamp null,
    constraint location_ibfk_1
        foreign key (id) references express.package (id)
);

create table logistic
(
    id           bigint                                   not null
        primary key,
    name         varchar(255)                          not null,
    parent_id    bigint                                   null,
    level        enum ('province', 'city', 'district') not null,
    district     varchar(255)                          null,
    city         varchar(255)                          null,
    province     varchar(255)                          null,
    contact_info varchar(255)                          null,
    constraint logistic_ibfk_1
        foreign key (parent_id) references express.logistic (id)
);

create index parent_id
    on logistic (parent_id);

create table package
(
    id               bigint                                                                                                 not null
        primary key,
    sign_date        timestamp                                                                                           null,
    shipment_id      bigint                                                                                              not null,
    status           enum ('pending', 'processing', 'in_transit', 'delivering', 'signed', 'cancelled') default 'pending' not null,
    batch_id         bigint                                                                                                 null,
    receiver_id      bigint                                                                                                 null,
    receiver_name    varchar(50)                                                                                         null,
    receiver_address varchar(255)                                                                                        null,
    receiver_phone   varchar(20)                                                                                         null,
    weight           double                                                                                              null,
    size             varchar(50)                                                                                         null,
    constraint package_customer_id_fk
        foreign key (receiver_id) references express.customer (id),
    constraint package_ibfk_2
        foreign key (batch_id) references express.batch (id),
    constraint package_shipment_id_fk
        foreign key (shipment_id) references express.shipment (id)
);

create index trans_id
    on package (batch_id);

create table shipment
(
    id          bigint                                                                         not null
        primary key,
    origin      bigint                                                                            not null,
    destination bigint                                                                            not null,
    price       decimal(10, 2)                                                                 not null,
    status      enum ('pending', 'cod_pending', 'paid', 'cancelled') default 'pending'         not null,
    customer_id bigint                                                                            not null,
    create_date timestamp                                            default CURRENT_TIMESTAMP null,
    type        int                                                                            null,
    constraint shipment_customer_id_fk
        foreign key (customer_id) references express.customer (id),
    constraint shipment_ibfk_1
        foreign key (origin) references express.logistic (id),
    constraint shipment_ibfk_2
        foreign key (destination) references express.logistic (id)
);

create index destination
    on shipment (destination);

create index origin
    on shipment (origin);

create table vehicle
(
    id         bigint auto_increment
        primary key,
    type       varchar(255) not null,
    shift      varchar(255) not null,
    coordinate point        null
);


