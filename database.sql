create table batch
(
    id          int                                 not null
        primary key,
    create_date timestamp default CURRENT_TIMESTAMP null,
    origin      int                                 not null,
    destination int                                 not null,
    responsible int                                 not null,
    status      enum ('in_trans', 'arrive')         not null,
    vehicle_id  int                                 null,
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
    id            int auto_increment
        primary key,
    username      varchar(255) not null,
    phone         varchar(20)  null,
    password_hash varchar(255) not null,
    salt          varchar(255) not null,
    address       json         not null,
    email         varchar(255) null,
    constraint phone
        unique (phone),
    constraint username
        unique (username)
);

create table employee
(
    id            int auto_increment
        primary key,
    name          varchar(255) not null,
    phone         varchar(20)  null,
    password_hash varchar(255) not null,
    salt          varchar(255) not null,
    serve_at      int          null,
    email         varchar(255) null,
    constraint phone
        unique (phone),
    constraint employee_ibfk_1
        foreign key (serve_at) references express.logistic (id)
);

create index serve_at
    on employee (serve_at);

create table logistic
(
    id           int                                   not null
        primary key,
    name         varchar(255)                          not null,
    parent_id    int                                   null,
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

create table shipment
(
    id          int                                                                            not null
        primary key,
    origin      int                                                                            not null,
    destination int                                                                            not null,
    price       decimal(10, 2)                                                                 not null,
    status      enum ('pending', 'cod_pending', 'paid', 'cancelled') default 'pending'         not null,
    customer_id int                                                                            not null,
    create_date timestamp                                            default CURRENT_TIMESTAMP null,
    constraint shipment_ibfk_1
        foreign key (origin) references express.logistic (id),
    constraint shipment_ibfk_2
        foreign key (destination) references express.logistic (id),
    constraint shipment_ibfk_3
        foreign key (customer_id) references express.customer (id)
);

create index customer_id
    on shipment (customer_id);

create index destination
    on shipment (destination);

create index origin
    on shipment (origin);

create table vehicle
(
    id         int auto_increment
        primary key,
    type       varchar(255) not null,
    shift      varchar(255) not null,
    coordinate point        null
);


