-- INSERT INTO user (user_id, email, user_name, password_hash, provider, created_by, created_date) VALUES
--     ( 1, 'admin@mail.com', 'admin', '$2a$04$ItDBvcn41QncTbDL6Q2eNOHVQf5US/bdwQj/aczc2AGoGoEaX.ogi', 'local', 'system', now());
--
-- INSERT INTO user (user_id, email, user_name, password_hash, provider, created_by, created_date) VALUES
--     ( 2, 'user1@mail.com', 'user1','$2a$04$Qao0KqDim7VubPHV9x.oj.awaz0ArpPNlU7l0q18ZdsBXJn82dKPG', 'local', 'system', now());
--
-- INSERT INTO authority (name) VALUES
--                                  ('ROLE_ADMIN'), ('ROLE_USER' );
--
-- INSERT INTO user_authority (user_id,authority_name) VALUES
--                                                         ( 1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');
INSERT INTO user (id, email, password, name, role) VALUES
    ( 1, 'admin@mail.com', '$2a$04$qaFN9ZOFCcX6Rc03pEQyTOjiZhEWjhWh.QywI.K46QnG0Od9Ig.Ku', 'admin', 'ADMIN');
INSERT INTO user (id, email, password, name, role) VALUES
    ( 2, 'user1@mail.com', '$2a$04$V1XTgAYyONuU2i3ri0vem.2jVdtEBZtmY/ONxsiNnC/XcRyuy96Iu', 'admin', 'USER');
INSERT INTO user (id, email, password, name, role) VALUES
    ( 3, 'user2@mail.com', '$2a$04$V1XTgAYyONuU2i3ri0vem.2jVdtEBZtmY/ONxsiNnC/XcRyuy96Iu', 'guest', 'GUEST');
-- password = 1234

INSERT INTO Posts (title, content, author, location, viewed, price) VALUES
    ( 'The standard Lorem '
    , '<p>"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."</p>'
    ,  'keumtae.kim'
    ,  5
    ,  12
    ,  1000);

INSERT INTO Posts (title, content, author, location, viewed, price) VALUES
    ( 'Section 1.10.32 of ""'
    , '<p>"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?"</p>'
    ,  'keumtae.kim'
    ,  10
    ,  45
    ,  1000);

INSERT INTO Posts (title, content, author, location, viewed, price) VALUES
    ( '1914 translation '
    , '<p>"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"</p>'
    ,  'keumtae.kim'
    ,  4
    ,  4
    ,  1000);

INSERT INTO Posts (title, content, author, location) VALUES
    ( 'Section 1.10.33 "'
    , '<p>"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."</p>'
    ,  'kang'
    ,  23);

INSERT INTO Posts (title, content, author, location) VALUES
    ( 'by H. Rackham'
    , '<p>"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."</p>'
    ,  'wata'
    ,  5);
INSERT INTO Posts (title, content, author) VALUES
    ( '1914. Rackham'
    , '<p>"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( 'Section  et Malorum"'
    , '<p>"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( 'by H. Rackham'
    , '<p>"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."</p>'
    ,  'keumtae.kim');
INSERT INTO Posts (title, content, author) VALUES
    ( ' by H. Rackham'
    , '<p>"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( 'de Finibus Bonorum et Malorum"'
    , '<p>"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( ' by H. Rackham'
    , '<p>"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."</p>'
    ,  'keumtae.kim');
INSERT INTO Posts (title, content, author) VALUES
    ( '1914 y H. Rackham'
    , '<p>"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( 'Sectiorum et Malorum"'
    , '<p>"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( '1914 trckham'
    , '<p>"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."</p>'
    ,  'keumtae.kim');
INSERT INTO Posts (title, content, author) VALUES
    ( '19ckham'
    , '<p>"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( 'Sectiont Malorum"'
    , '<p>"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."</p>'
    ,  'keumtae.kim');

INSERT INTO Posts (title, content, author) VALUES
    ( '191kham'
    , '<p>"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."</p>'
    ,  'keumtae.kim');

INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 1, 1, 1, '1.jpg', 'src/main/resources/testImages/upload/2022/09/17/1.jpg', 'src/main/resources/testImages/upload/2022/09/17/s_1.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 9, 1, 2, '9.jpg', 'src/main/resources/testImages/upload/2022/09/26/24_fb-logo.png', 'src/main/resources/testImages/upload/2022/09/26/24_fb-logo.png', 'src/main/resources/testImages/upload/2022/09/26/');

INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 2, 2, 1, '2.jpg', 'src/main/resources/testImages/upload/2022/09/17/2.jpg', 'src/main/resources/testImages/upload/2022/09/17/s_2.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 3, 3, 1, '3.jpg', 'src/main/resources/testImages/upload/2022/09/17/4.jpg', 'src/main/resources/testImages/upload/2022/09/17/s_4.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 4, 4, 1, '4.jpg', 'src/main/resources/testImages/upload/2022/09/17/4.jpg', 'src/main/resources/testImages/upload/2022/09/17/s_4.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 5, 5, 1, '5.jpg', 'src/main/resources/testImages/upload/2022/09/17/5.jpg' , 'src/main/resources/testImages/upload/2022/09/17/s_5.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 6, 6, 1, '6.jpg', 'src/main/resources/testImages/upload/2022/09/17/6.jpg', 'src/main/resources/testImages/upload/2022/09/17/s_6.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 7, 7, 1, '7.jpg', 'src/main/resources/testImages/upload/2022/09/17/7.jpg', 'src/main/resources/testImages/upload/2022/09/17/s_7.jpg', 'src/main/resources/testImages/upload/2022/09/17/' );
INSERT INTO Pictures (id, board_id, idx, original_file_name, saved_file_name, salted_file_name, stored_folder_path) VALUES
    ( 8, 8, 1,'8.png', 'src/main/resources/testImages/upload/2022/09/17/8.png', 'src/main/resources/testImages/upload/2022/09/17/s_8.png', 'src/main/resources/testImages/upload/2022/09/17/' );

-- INSERT INTO CHATROOM (id, seller_id, buyer_id) VALUES
--     (1, 1, 2);
-- INSERT INTO comment (body, created_date, last_modified_date, post_id, user_id) VALUES
--     ( 'Nunc et enim ut metus ultricies porta. Praesent cursus sit amet sem a ultrices. In sollicitudin vestibulum eros eu pretium.'
--     , now()-5, now()-5, 1, 1);
--
-- INSERT INTO comment (body, created_date, last_modified_date, post_id, user_id) VALUES
--     ( 'hF-E focuses attention on the message itself, in both form and content. Such translations then would be concerned with such correspondences as poetry to poetry.'
--     , now()-5, now()-5, 1, 2);
