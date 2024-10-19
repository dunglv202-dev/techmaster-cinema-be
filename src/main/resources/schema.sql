drop database if exists `techmaster`;
create database `techmaster`;
use techmaster;
drop table if exists `booking`;
drop table if exists `booking_seats`;
drop table if exists `category`;
drop table if exists `cinema`;
drop table if exists `location`;
drop table if exists `movie`;
drop table if exists `movie_categories`;
drop table if exists `room`;
drop table if exists `schedule`;
drop table if exists `user`;
create table `booking` (`grand_total` float(53) not null, `created_at` datetime(6), `created_by` bigint, `id` bigint not null auto_increment, `payment_deadline` datetime(6), `schedule_id` bigint, `timestamp` datetime(6), `updated_at` datetime(6), `updated_by` bigint, `user_id` bigint, `payment_ref` varchar(255), `status` enum ('CANCELED','PAID','PENDING_PAYMENT'), primary key (`id`)) engine=InnoDB;
create table `booking_seats` (`booking_id` bigint not null, `seats` varchar(255)) engine=InnoDB;
create table `category` (`id` integer not null auto_increment, `code` varchar(255), `name` varchar(255), `thumbnail` varchar(255), primary key (`id`)) engine=InnoDB;
create table `cinema` (`location_id` integer, `created_at` datetime(6), `created_by` bigint, `id` bigint not null auto_increment, `updated_at` datetime(6), `updated_by` bigint, `name` varchar(255), `seats` json, primary key (`id`)) engine=InnoDB;
create table `location` (`id` integer not null auto_increment, `code` varchar(255), `name` varchar(255), primary key (`id`)) engine=InnoDB;
create table `movie` (`duration_in_minutes` integer not null, `created_at` datetime(6), `created_by` bigint, `final_screening` datetime(6), `id` bigint not null auto_increment, `premiere_date` datetime(6), `updated_at` datetime(6), `updated_by` bigint, `description` varchar(255), `director` varchar(255), `name` varchar(255), `thumbnail` varchar(255), `trailer` varchar(255), `actors` json, primary key (`id`)) engine=InnoDB;
create table `movie_categories` (`categories_id` integer not null, `movie_id` bigint not null) engine=InnoDB;
create table `room` (`cinema_id` bigint, `created_at` datetime(6), `created_by` bigint, `id` bigint not null auto_increment, `updated_at` datetime(6), `updated_by` bigint, `name` varchar(255), primary key (`id`)) engine=InnoDB;
create table `schedule` (`normal_price` float(53), `vip_price` float(53), `cinema_id` bigint, `created_at` datetime(6), `created_by` bigint, `end` datetime(6), `id` bigint not null auto_increment, `movie_id` bigint, `room_id` bigint, `start` datetime(6), `updated_at` datetime(6), `updated_by` bigint, `seats` json, primary key (`id`)) engine=InnoDB;
create table `user` (`birth_date` date, `created_at` datetime(6), `created_by` bigint, `id` bigint not null auto_increment, `updated_at` datetime(6), `updated_by` bigint, `email` varchar(255), `first_name` varchar(255), `last_name` varchar(255), `password` varchar(255), `phone` varchar(255), primary key (`id`)) engine=InnoDB;
alter table `booking` add constraint `FKqe7j2rxpaxt4i77pcncddhaig` foreign key (`created_by`) references `user` (`id`);
alter table `booking` add constraint `FKjuarbxfmtimqyptd7ebf0t0sm` foreign key (`updated_by`) references `user` (`id`);
alter table `booking` add constraint `FKthjqqe79bpf9ny9r8hfcpu8jf` foreign key (`schedule_id`) references `schedule` (`id`);
alter table `booking` add constraint `FKpr2wacfs0vxh9v61dni9ugonn` foreign key (`user_id`) references `user` (`id`);
alter table `booking_seats` add constraint `FK5whwx9stp30wpiw657xky636p` foreign key (`booking_id`) references `booking` (`id`);
alter table `cinema` add constraint `FKsow6mflcn7ndlx6oyjsmjnfyj` foreign key (`created_by`) references `user` (`id`);
alter table `cinema` add constraint `FKgh5jpvqai8rjyujqsf7me27wg` foreign key (`updated_by`) references `user` (`id`);
alter table `cinema` add constraint `FK6369qnhxb6a4ml2oiqsd0j3js` foreign key (`location_id`) references `location` (`id`);
alter table `movie` add constraint `FKl61ie62x6s5ph4v0nrfrj1s6r` foreign key (`created_by`) references `user` (`id`);
alter table `movie` add constraint `FK8kkt2gyav441hco4kxqsihswo` foreign key (`updated_by`) references `user` (`id`);
alter table `movie_categories` add constraint `FK7d1p3f7as3teytxe63d7gqyyq` foreign key (`categories_id`) references `category` (`id`);
alter table `movie_categories` add constraint `FKe85p7f1l9rkqjexxaadfagw7f` foreign key (`movie_id`) references `movie` (`id`);
alter table `room` add constraint `FKlgvew019i7l0hu6ikrwxnmi47` foreign key (`created_by`) references `user` (`id`);
alter table `room` add constraint `FK6ct1e4q31l7jubh53j020kqpa` foreign key (`updated_by`) references `user` (`id`);
alter table `room` add constraint `FKkurghg6yd2t6q8xn56w4jovkq` foreign key (`cinema_id`) references `cinema` (`id`);
alter table `schedule` add constraint `FKiiho91ned573uf04bgm5ksawj` foreign key (`created_by`) references `user` (`id`);
alter table `schedule` add constraint `FKcvdyt3liedi3x650g6x93lca` foreign key (`updated_by`) references `user` (`id`);
alter table `schedule` add constraint `FKa3k968pm1nuiw3cu8rstvqy5i` foreign key (`cinema_id`) references `cinema` (`id`);
alter table `schedule` add constraint `FK2ejq8k7o5t9njcaftutle25sj` foreign key (`movie_id`) references `movie` (`id`);
alter table `schedule` add constraint `FKdwtxgpxiqy9fi013isa1w42gs` foreign key (`room_id`) references `room` (`id`);
alter table `user` add constraint `FKtgx1nsmw2irw5u69hibdx5t8b` foreign key (`created_by`) references `user` (`id`);
alter table `user` add constraint `FKap4v54a63igjjm8tegdgji11m` foreign key (`updated_by`) references `user` (`id`);