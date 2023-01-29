delete from INGREDIENT_REF;
delete from TACO;
delete from TACO_ORDER;
delete from INGREDIENT;

insert into Ingredient (id, name, type) values ( 'FITO', 'Flour Tortilla', 'WRAP' );
insert into Ingredient (id, name, type) values ( 'COTO', 'Corn Tortilla', 'WRAP' );
insert into Ingredient (id, name, type) values ( 'GRBF', 'Ground Beaf', 'PROTEIN' );
insert into Ingredient (id, name, type) values ( 'CARN', 'Carnitas', 'PROTEIN' );
insert into Ingredient (id, name, type) values ( 'TMTO', 'Diced Tomatoes', 'VEGGIES' );
insert into Ingredient (id, name, type) values ( 'LETC', 'Lettuce', 'VEGGIES' );
insert into Ingredient (id, name, type) values ( 'CHED', 'Cheddar', 'CHEESE' );
insert into Ingredient (id, name, type) values ( 'JACK', 'Monterrey Jack', 'CHEESE' );
insert into Ingredient (id, name, type) values ( 'SLSA', 'Salsa', 'SAUCE' );
insert into Ingredient (id, name, type) values ( 'SRCR', 'Sour Cream', 'SAUCE' );