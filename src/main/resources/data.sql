INSERT INTO author
(ID, FIRST_NAME, LAST_NAME)
VALUES
(1, 'Joanne', 'Rowling'),
(2, 'Herbert', 'Schildt'),
(3, 'Ranga Rao', 'Karanam ')
;

INSERT INTO book
(ID, BORROWED, DELETED, ISBN_NUMBER, SUMMARY, TITLE, AUTHOR_ID)
VALUES
(1, false, false, '9781234567897', 'the first book', 'Harry Potter and the Philosopher''s Stone', 1),
(2, false, false, '9782123456803', 'the second book', 'Harry Potter and the Chamber of Secrets''s', 1),
(3, false, false, '9794523654896', 'java programming', 'Java: A Beginner''s Guide, Seventh Edition', 2),
(4, false, false, '9794856214578', 'spring framework', 'Spring: Microservices with Spring Boot', 3)
;
