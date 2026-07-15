-- Library Management System - Sample Data
USE library_management;

INSERT INTO books (title, author, category, isbn, quantity, available_quantity) VALUES
('Effective Java',              'Joshua Bloch',       'Programming',  '978-0134685991', 5, 5),
('Clean Code',                  'Robert C. Martin',   'Programming',  '978-0132350884', 4, 4),
('Head First Java',             'Kathy Sierra',       'Programming',  '978-0596009205', 6, 6),
('The Pragmatic Programmer',    'David Thomas',       'Programming',  '978-0135957059', 3, 3),
('Introduction to Algorithms',  'Thomas H. Cormen',   'Computer Science', '978-0262046305', 2, 2),
('Database System Concepts',    'Abraham Silberschatz','Computer Science', '978-0078022159', 3, 3),
('Java: The Complete Reference', 'Herbert Schildt',   'Programming',  '978-1260440232', 4, 4),
('Design Patterns',             'Gang of Four',       'Software Engineering', '978-0201633610', 2, 2);

INSERT INTO users (full_name, email, phone) VALUES
('Sonu Singh',    'sonu.singh@codveda.com',   '9876543210'),
('Priya Sharma',  'priya.sharma@codveda.com', '9876543211'),
('Rahul Verma',   'rahul.verma@codveda.com',  '9876543212'),
('Anita Desai',   'anita.desai@codveda.com',  '9876543213');
