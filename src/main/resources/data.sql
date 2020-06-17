INSERT INTO author
(ID, FIRST_NAME, LAST_NAME)
VALUES
(1, 'Joanne', 'Rowling');

INSERT INTO book
(ID, BORROWED, DELETED, ISBN_NUMBER, SUMMARY, TITLE, AUTHOR_ID)
VALUES
(1, false, false, '9781234567897', 'the first book', 'Harry Potter and the Philosopher''s Stone', 1),
(2, false, false, '9782123456803', 'the second book', 'Harry Potter and the Chamber of Secrets''s', 1);
