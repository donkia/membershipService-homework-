insert into store (id, name, category) values (default, 'abc-mart', 'A');
insert into store (id, name, category) values (default, 'df-mart', 'B');
insert into member (created_date,  member_id) values  ('2022-06-25T11:54:02.224823600', '000000001');
insert into barcode(member_id, barcode_id) values ('000000001', '1071637887');

insert into account (id,  barcode, price, store_category) values (default, '1071637887', 0,'A');
insert into account (id,  barcode, price, store_category) values (default, '1071637887', 0,'B');
insert into account (id,  barcode, price, store_category) values (default, '1071637887', 0,'C');


insert into point(id, approved_at, barcode, category, price, store_name, type) values (default, '2022-06-24', '1071637887', 'A', 500, 'aa', 'earn');
insert into point(id, approved_at, barcode, category, price, store_name, type) values (default, '2022-06-23', '1071637887', 'A', 100, 'aa', 'earn');
insert into point(id, approved_at, barcode, category, price, store_name, type) values (default, '2022-06-25', '1071637887', 'A', 200, 'aa', 'use');
insert into point(id, approved_at, barcode, category, price, store_name, type) values (default, '2022-06-25', '1071637887', 'B', 200, 'aa', 'earn');
insert into point(id, approved_at, barcode, category, price, store_name, type) values (default, '2022-06-23', '1071637887', 'B', 20, 'aa', 'use');