INSERT INTO tb_users(id, email, password, created_at) VALUES (1, 'email@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z');
INSERT INTO tb_users(id, email, password, created_at) VALUES (2, 'test@email.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z');

INSERT INTO tb_categories(id, name, mother_category_id) VALUES (1, 'Geral', null);

INSERT INTO tb_products(id, name, price, quantity, description, category_id, owner_id, created_at)
VALUES (1, 'Produto novinho', 200, 200, 'Só comprar e levar', 1, 1, TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z');

INSERT INTO tb_specifics(id, name, description) VALUES (1, 'Qualidade', 'Muito Boa');
INSERT INTO tb_specifics(id, name, description) VALUES (2, 'Tamanho', 'Quase um relógio do faustão');
INSERT INTO tb_specifics(id, name, description) VALUES (3, 'Preço', 'Cabe no seu bolso');

INSERT INTO tb_products_specifics(specifics_id, products_id) VALUES (1, 1);
INSERT INTO tb_products_specifics(specifics_id, products_id) VALUES (2, 1);
INSERT INTO tb_products_specifics(specifics_id, products_id) VALUES (3, 1);