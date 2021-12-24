create table Address
(
    ID          int auto_increment,
    Client_ID   int         not null,
    street      varchar(50) null,
    number      varchar(50) null,
    city        varchar(50) null,
    postal_code varchar(50) null,
    country     varchar(50) null,
    primary key (ID, Client_ID),
    constraint Adress_ID_uindex
        unique (ID)
);

create index fk_Adress_Client1_idx
    on Address (Client_ID);

create table AssociatedStaff
(
    DNI        varchar(50)                                                       not null
        primary key,
    name       varchar(50)                                                       not null,
    last_name1 varchar(50)                                                       null,
    last_name2 varchar(50)                                                       null,
    state      enum ('Active', 'Inactive', 'Blocked', 'Closed') default 'Active' not null,
    constraint DNI_UNIQUE
        unique (DNI)
);

create table BankAccount
(
    IBAN               varchar(50) not null
        primary key,
    EburyAccount_id    int         not null,
    EburyAccount_owner int         not null,
    SWIFT              varchar(50) null,
    curency            varchar(50) null,
    country            varchar(50) null
);

create table Client
(
    ID            int auto_increment
        primary key,
    status        enum ('Active', 'Inactive', 'Blocked', 'Closed') default 'Active' not null,
    NIF           varchar(50)                                                       not null,
    name          varchar(50)                                                       not null,
    last_name1    varchar(50)                                                       null,
    last_name2    varchar(50)                                                       null,
    birth_date    date                                                              null,
    register_date date                                                              not null,
    end_date      date                                                              null,
    address_id    int                                                               null,
    constraint Client_ID_uindex
        unique (ID),
    constraint address_fk
        foreign key (address_id) references Address (ID)
);

alter table Address
    add constraint fk_Adress_Client1
        foreign key (Client_ID) references Client (ID);

create index address_fk_idx
    on Client (address_id);

create table EburyAccount
(
    id               int auto_increment
        primary key,
    owner            int                                                               not null,
    BankAccount_IBAN varchar(50)                                                       not null,
    status           enum ('Active', 'Inactive', 'Blocked', 'Closed') default 'Active' not null,
    accounttype      enum ('Pooled', 'Dedicada', 'Segregada')                          not null,
    registerdate     date                                                              not null,
    closedate        date                                                              null,
    constraint id_UNIQUE
        unique (id),
    constraint fk_EburyAccount_BankAccount1
        foreign key (BankAccount_IBAN) references BankAccount (IBAN),
    constraint fk_EburyAccount_Client1
        foreign key (owner) references Client (ID)
);

create index fk_EburyAccount_BankAccount1_idx
    on EburyAccount (BankAccount_IBAN);

create index fk_EburyAccount_Client1_idx
    on EburyAccount (owner);

create table Login
(
    user     varchar(50)                           not null
        primary key,
    password varchar(50)                           not null,
    rol      enum ('User', 'Regler', 'Regelgever') not null,
    AS_FK    varchar(50)                           null,
    constraint AS_FK_UNIQUE
        unique (AS_FK),
    constraint user_UNIQUE
        unique (user),
    constraint fk_Login_AssociatedStaff
        foreign key (AS_FK) references AssociatedStaff (DNI)
);

create table Operation
(
    id               int auto_increment,
    BankAccount_IBAN varchar(50) not null,
    EburyAccount_id  int         not null,
    date             date        not null,
    amount           float       not null,
    conversionrate   float       null,
    comission        float       null,
    beneficiary      varchar(50) null,
    primary key (id, BankAccount_IBAN, EburyAccount_id),
    constraint id_UNIQUE
        unique (id),
    constraint fk_Operation_BankAccount1
        foreign key (BankAccount_IBAN) references BankAccount (IBAN),
    constraint fk_Operation_EburyAccount1
        foreign key (EburyAccount_id) references EburyAccount (id)
);

create index fk_Operation_BankAccount1_idx
    on Operation (BankAccount_IBAN);

create index fk_Operation_EburyAccount1_idx
    on Operation (EburyAccount_id);

create table Relation
(
    AssociatedStaff_DNI varchar(50) not null,
    Client_ID           int         not null,
    start               date        null,
    end                 date        null,
    primary key (Client_ID, AssociatedStaff_DNI),
    constraint fk_Relation_AssociatedStaff1
        foreign key (AssociatedStaff_DNI) references AssociatedStaff (DNI),
    constraint fk_Relation_Client1
        foreign key (Client_ID) references Client (ID)
);

create index fk_Relation_AssociatedStaff1_idx
    on Relation (AssociatedStaff_DNI);

create index fk_Relation_Client1_idx
    on Relation (Client_ID);

create table Relation_has_EburyAccount
(
    Relation_AssociatedStaff_DNI varchar(50) not null,
    Relation_Client_ID           int         not null,
    EburyAccount_id              int         not null,
    primary key (Relation_AssociatedStaff_DNI, Relation_Client_ID,
                 EburyAccount_id),
    constraint fk_Relation_has_EburyAccount_EburyAccount1
        foreign key (EburyAccount_id) references EburyAccount (id),
    constraint fk_Relation_has_EburyAccount_Relation1
        foreign key (Relation_AssociatedStaff_DNI,
                     Relation_Client_ID) references Relation (AssociatedStaff_DNI, Client_ID)
);

create index fk_Relation_has_EburyAccount_EburyAccount1_idx
    on Relation_has_EburyAccount (EburyAccount_id);

create index fk_Relation_has_EburyAccount_Relation1_idx
    on Relation_has_EburyAccount (Relation_AssociatedStaff_DNI,
                                  Relation_Client_ID);

