create table if not exists customers
(
    id            int auto_increment
        primary key,
    username      varchar(255) not null,
    phone         varchar(20)  not null,
    password_hash varchar(255) not null,
    salt          varchar(255) not null,
    address       json         not null,
    constraint phone
        unique (phone),
    constraint username
        unique (username)
);

create table if not exists logistics
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
    constraint logistics_ibfk_1
        foreign key (parent_id) references logistics (id)
);

create table if not exists employees
(
    id            int auto_increment
        primary key,
    name          varchar(255) not null,
    phone         varchar(20)  not null,
    password_hash varchar(255) not null,
    salt          varchar(255) not null,
    serve_at      int          null,
    constraint phone
        unique (phone),
    constraint employees_ibfk_1
        foreign key (serve_at) references logistics (id)
);

create index serve_at
    on employees (serve_at);

create index parent_id
    on logistics (parent_id);

create table if not exists shipments
(
    id          int                                                                            not null
        primary key,
    origin      int                                                                            not null,
    destination int                                                                            not null,
    price       decimal(10, 2)                                                                 not null,
    status      enum ('pending', 'cod_pending', 'paid', 'cancelled') default 'pending'         not null,
    customer_id int                                                                            not null,
    create_date timestamp                                            default CURRENT_TIMESTAMP null,
    constraint shipments_ibfk_1
        foreign key (origin) references logistics (id),
    constraint shipments_ibfk_2
        foreign key (destination) references logistics (id),
    constraint shipments_ibfk_3
        foreign key (customer_id) references customers (id)
);

create index customer_id
    on shipments (customer_id);

create index destination
    on shipments (destination);

create index origin
    on shipments (origin);

create table if not exists vehicles
(
    id         int auto_increment
        primary key,
    type       varchar(255) not null,
    shift      varchar(255) not null,
    coordinate point        null
);

create table if not exists batches
(
    id          int                                 not null
        primary key,
    create_date timestamp default CURRENT_TIMESTAMP null,
    origin      int                                 not null,
    destination int                                 not null,
    responsible int                                 not null,
    status      enum ('in_trans', 'arrive')         not null,
    vehicle_id  int                                 null,
    constraint batches_ibfk_1
        foreign key (origin) references logistics (id),
    constraint batches_ibfk_2
        foreign key (destination) references logistics (id),
    constraint batches_ibfk_3
        foreign key (responsible) references employees (id),
    constraint batches_ibfk_4
        foreign key (vehicle_id) references vehicles (id)
);

create index destination
    on batches (destination);

create index origin
    on batches (origin);

create index responsible
    on batches (responsible);

create index vehicle_id
    on batches (vehicle_id);

create table if not exists packages
(
    id               int                                                                                                 not null
        primary key,
    sign_date        timestamp                                                                                           null,
    shipment_id      int                                                                                                 not null,
    status           enum ('pending', 'processing', 'in_transit', 'delivering', 'signed', 'cancelled') default 'pending' not null,
    trans_id         int                                                                                                 null,
    receiver_id      int                                                                                                 null,
    receiver_name    varchar(50)                                                                                         null,
    receiver_address varchar(255)                                                                                        null,
    receiver_phone   varchar(20)                                                                                         null,
    constraint packages_customers_id_fk
        foreign key (receiver_id) references customers (id),
    constraint packages_ibfk_1
        foreign key (shipment_id) references shipments (id),
    constraint packages_ibfk_2
        foreign key (trans_id) references batches (id)
);

create index shipment_id
    on packages (shipment_id);

create index trans_id
    on packages (trans_id);


