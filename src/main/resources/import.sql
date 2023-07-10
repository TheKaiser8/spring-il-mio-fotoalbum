INSERT INTO `photos` (`title`, `description`, `url`, `is_visible`, `created_at`) VALUES ('Spiaggia dei 3 mari', 'Una vista panoramica mozzafiato che mostra una spiaggia unica, bagnata apparentemente da 3 diversi mari.', '/img/foto-mare.jpg', true, '2023-07-07 12:00');
INSERT INTO `photos` (`title`, `description`, `url`, `is_visible`, `created_at`) VALUES ('Riflessi urbani', 'Un\'immagine notturna che cattura i riflessi dei grattacieli nella superficie dell\'acqua.', '/img/riflessi-urbani.jpeg', false, '2023-07-02 23:15');
INSERT INTO `photos` (`title`, `description`, `url`, `is_visible`, `created_at`) VALUES ('Tramonto in montagna', 'Uno spettacolare tramonto che colora il cielo e le cime delle montagne con tonalità calde e suggestive. L\'atmosfera magica e tranquilla della montagna si fonde con il fascino del sole che si abbassa sull\'orizzonte.', '/img/tramonto-montagna.jpeg', true, '2023-04-12 19:00');
INSERT INTO `photos` (`title`, `description`, `url`, `is_visible`, `created_at`) VALUES ('Riflessi sul lago', 'Uno straordinario arcobaleno di ciottoli colorati. Il lago McDonald è il più grande dei laghi del Glacier National Park, un immenso parco nello stato americano del Montana, al confine con il Canada.', '/img/lago-mcdonald.jpg', false, '2023-06-29 19:30');
INSERT INTO `categories` (`name`, `description`) VALUES ('Paesaggi', null);
INSERT INTO `categories` (`name`, `description`) VALUES ('Mare', null);
INSERT INTO `categories` (`name`, `description`) VALUES ('Montagna', null);
INSERT INTO `categories` (`name`, `description`) VALUES ('Invernale', null);
INSERT INTO `categories` (`name`, `description`) VALUES ('Estate', null);
INSERT INTO `categories` (`name`, `description`) VALUES ('Architettura', null);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (1, 1);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (1, 2);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (1, 5);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (2, 6);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (3, 1);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (3, 3);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (3, 4);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (4, 1);
INSERT INTO `photo_category` (`photo_id`, `category_id`) VALUES (4, 3);
INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`) VALUES (1, 'giovanni@email.com', 'Giovanni', 'Storti', '{noop}john');
INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`) VALUES (2, 'giacomo@email.com', 'Giovanni', 'Poretti', '{noop}jack');
INSERT INTO `roles` (`id`, `name`) VALUES (1, 'ADMIN');
INSERT INTO `roles` (`id`, `name`) VALUES (2, 'USER');
INSERT INTO `users_roles` (`user_id`, `roles_id`) VALUES (1, 1);
INSERT INTO `users_roles` (`user_id`, `roles_id`) VALUES (2, 2);