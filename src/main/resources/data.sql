INSERT INTO client (id, name)
VALUES (1, 'JohnDoe');
INSERT INTO client (id, name)
VALUES (2, 'Rennie');
INSERT INTO client (id, name)
VALUES (3, 'BlaBlaBlaBla');
INSERT INTO client (id, name)
VALUES (4, 'AdamDoe');

INSERT INTO to_rent (id, name, price, area, description)
VALUES (1, 'LaCastle', '999', 100.2, 'Simple description');
INSERT INTO to_rent (id, name, price, area, description)
VALUES (2, 'LaLasVegas', '250', 234.2, 'Simple another description');
INSERT INTO to_rent (id, name, price, area, description)
VALUES (3, 'LaLand', '50', 235.2, 'Simple simple description');
INSERT INTO to_rent (id, name, price, area, description)
VALUES (4, 'LaTom', '123', 15345.2, 'Very simple description');

INSERT INTO reservation (id, start_rent_date, end_rent_date, cost, tenant_id, landlord_id, to_rent_id)
VALUES (1, '2023-01-01', '2023-02-01', 100, 1, 2, 3);
INSERT INTO reservation (id, start_rent_date, end_rent_date, cost, tenant_id, landlord_id, to_rent_id)
VALUES (2, '2023-05-01', '2023-07-23', 100, 1, 2, 3);
INSERT INTO reservation (id, start_rent_date, end_rent_date, cost, tenant_id, landlord_id, to_rent_id)
VALUES (3, '2023-10-10', '2023-10-15', 100, 1, 2, 1);
INSERT INTO reservation (id, start_rent_date, end_rent_date, cost, tenant_id, landlord_id, to_rent_id)
VALUES (4, '2023-09-01', '2023-09-05', 100, 1, 2, 4);