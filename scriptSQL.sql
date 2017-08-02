/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Asistente
 * Created: 01-ago-2017
 */

insert into BRANDENTITY ( name ) values ('Mazda');
insert into BRANDENTITY ( name ) values ('volkswagen');
insert into BRANDENTITY ( name ) values ('Kia');
insert into BRANDENTITY ( name ) values ('Mercedes Benz');
insert into BRANDENTITY ( name ) values ('Hyunday');
insert into BRANDENTITY ( name ) values ('Volvo');
insert into BRANDENTITY ( name ) values ('Chevrolet');

insert into CATEGORYENTITY ( name ) values('camioneta');
insert into CATEGORYENTITY ( name ) values('sedan');
insert into CATEGORYENTITY ( name ) values('coupe');
insert into CATEGORYENTITY ( name ) values('cabriolet');

insert into CARENTITY (image, name, price, revisions, warranty, brand_id, category_id) values ('https://co.mazdacdn.com/vpp/assets/thumbnails/cx5thumb.png','Mazda CX-5',150000000,10,140000,1,1);
insert into CARENTITY (image, name, price, revisions, warranty, brand_id, category_id) values ('https://co.mazdacdn.com/vpp/assets/noticias/noticiasel-nuevo-mazda3.jpg','Mazda 3',65000000,10,140000,1,2);
insert into CARENTITY (image, name, price, revisions, warranty, brand_id, category_id) values ('http://cdn1.autoexpress.co.uk/sites/autoexpressuk/files/mazda-6-coupe.jpg','Mazda coupe',65000000,10,140000,1,3);
insert into CARENTITY (image, name, price, revisions, warranty, brand_id, category_id) values ('https://www.mazdausa.com/siteassets/vehicles/2017/mx-5-soft-top/360-studio/crystal-white/360-mx5-st-crystalwhite-extonly-05.jpg','Mazda cabriolet',65000000,10,140000,1,4);
