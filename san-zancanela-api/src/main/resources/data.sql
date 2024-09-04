INSERT INTO clients (id, name) VALUES
    ('123', 'João Silva'),
    ('456', 'Maria Oliveira'),
    ('789', 'Pedro Santos');
INSERT INTO charges (id, original_amount, client_id) VALUES
  ('123', 100.00, '123'),
  ('321', 20.78, '123'),
  ('654', 57.04, '123'),
  ('987', 1000.74, '123'),
  ('456', 200.00, '456'),
  ('147', 100.00, '456'),
  ('258', 100.00, '456'),
  ('789', 300.00, '789');