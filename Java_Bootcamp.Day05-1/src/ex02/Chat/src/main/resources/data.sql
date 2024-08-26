INSERT INTO chat.user(login, password) VALUES ('User1', '111');
INSERT INTO chat.user(login, password) VALUES ('User2', '222');
INSERT INTO chat.user(login, password) VALUES ('User3', '333');
INSERT INTO chat.user(login, password) VALUES ('User4', '444');
INSERT INTO chat.user(login, password) VALUES ('User5', '555');
INSERT INTO chat.user(login, password) VALUES ('User6', '666');

INSERT INTO chat.room(name, owner) VALUES ('Chat1', 1);
INSERT INTO chat.room(name, owner) VALUES ('Chat2', 2);
INSERT INTO chat.room(name, owner) VALUES ('Chat3', 3);
INSERT INTO chat.room(name, owner) VALUES ('Chat4', 4);
INSERT INTO chat.room(name, owner) VALUES ('Chat5', 5);
INSERT INTO chat.room(name, owner) VALUES ('Chat6', 6);

INSERT INTO chat.message(author, room, text) VALUES (1, 2, 'Message in room 2 from author 1');
INSERT INTO chat.message(author, room, text) VALUES (1, 6, 'Message in room 6 from author 1');
INSERT INTO chat.message(author, room, text) VALUES (4, 1, 'Message in room 1 from author 4');
INSERT INTO chat.message(author, room, text) VALUES (3, 3, 'Message in room 3 from author 3');
INSERT INTO chat.message(author, room, text) VALUES (2, 5, 'Message in room 5 from author 2');
INSERT INTO chat.message(author, room, text) VALUES (5, 6, 'Message in room 6 from author 5');