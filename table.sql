CREATE TABLE `USER` (
	`email` VARCHAR(64) NOT NULL UNIQUE,
    `password` CHAR(60) NOT NULL,
    `nickname` VARCHAR(9) NOT NULL UNIQUE,
    `profile` VARCHAR(255) NOT NULL,
    `change_possible` TINYINT NOT NULL,
    PRIMARY KEY (`email`)
);

CREATE TABLE `TODOLIST` (
	`todolist_id` BINARY(16) NOT NULL UNIQUE,
    `user_email` VARCHAR(64) NOT NULL,
    `date` DATE NOT NULL,
    PRIMARY KEY (`todolist_id`),
    FOREIGN KEY (`user_email`)
		REFERENCES `USER` (`email`) ON DELETE CASCADE
);

CREATE TABLE `TODOLIST_SUBJECT` (
	`todolist_subject_id` BINARY(16) NOT NULL UNIQUE,
    `todolist_id` BINARY(16) NOT NULL,
	`subject` VARCHAR(31) NOT NULL,
    `value` INT NOT NULL,
    PRIMARY KEY (`todolist_subject_id`),
    FOREIGN KEY (`todolist_id`)
		REFERENCES `TODOLIST` (`todolist_id`) ON DELETE CASCADE
);

CREATE TABLE `TODOLIST_CONTENT` (
	`todolist_content_id` BINARY(16) NOT NULL UNIQUE,
    `todolist_subject_id` BINARY(16) NOT NULL,
    `content` VARCHAR(31) NOT NULL,
    `value` INT NOT NULL,
    `is_success` TINYINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`todolist_content_id`),
    FOREIGN KEY (`todolist_subject_id`)
		REFERENCES `TODOLIST_SUBJECT` (`todolist_subject_id`) ON DELETE CASCADE
);

CREATE TABLE `FRIEND_APPLY` (
	`friend_email` VARCHAR(64) NOT NULL,
    `user_email` VARCHAR(64) NOT NULL,
    `created_at` DATE NOT NULL,
    PRIMARY KEY (`friend_email`, `user_email`),
    FOREIGN KEY (`friend_email`)
        REFERENCES `USER` (`email`) ON DELETE CASCADE,
    FOREIGN KEY (`user_email`)
		REFERENCES `USER` (`email`) ON DELETE CASCADE
);

CREATE TABLE `FRIEND` (
	`friend_email` VARCHAR(64) NOT NULL,
    `user_email` VARCHAR(64) NOT NULL,
    `created_at` DATE NOT NULL,
    PRIMARY KEY (`friend_email`, `user_email`),
    FOREIGN KEY (`friend_email`)
        REFERENCES `USER` (`email`) ON DELETE CASCADE,
    FOREIGN KEY (`user_email`)
        REFERENCES `USER` (`email`) ON DELETE CASCADE
);

CREATE TABLE `TEMPLATE` (
	`template_id` BINARY(16) NOT NULL UNIQUE,
    `user_email` VARCHAR(64) NOT NULL,
    `size` INT NOT NULL DEFAULT 7,
    `title` VARCHAR(9) NOT NULL,
    `profile` VARCHAR(255) NOT NULL,
    `created_at` DATE NOT NULL,
    PRIMARY KEY (`template_id`),
    FOREIGN KEY (`user_email`)
		REFERENCES `USER` (`email`) ON DELETE CASCADE
);

CREATE TABLE `TEMPLATE_DAY` (
	`template_day_id` BINARY(16) NOT NULL UNIQUE,
    `template_id` BINARY(16) NOT NULL,
    `day` INT NOT NULL,
	PRIMARY KEY (`template_day_id`),
    FOREIGN KEY (`template_id`)
		REFERENCES `TEMPLATE` (`template_id`) ON DELETE CASCADE
);

CREATE TABLE `TEMPLATE_TODOLIST_SUBJECT` (
	`template_todolist_subject_id` BINARY(16) NOT NULL UNIQUE,
    `template_day_id` BINARY(16) NOT NULL,
    `subject` VARCHAR(31) NOT NULL,
    `value` INT NOT NULL,
	PRIMARY KEY (`template_todolist_subject_id`),
    FOREIGN KEY (`template_day_id`)
		REFERENCES `TEMPLATE_DAY` (`template_day_id`) ON DELETE CASCADE
);

CREATE TABLE `TEMPLATE_TODOLIST_CONTENT` (
	`template_todolist_content_id` BINARY(16) NOT NULL UNIQUE,
    `template_todolist_subject_id` BINARY(16) NOT NULL,
    `content` VARCHAR(31) NOT NULL,
    `value` INT NOT NULL,
	PRIMARY KEY (`template_todolist_content_id`),
    FOREIGN KEY (`template_todolist_subject_id`)
		REFERENCES `TEMPLATE_TODOLIST_SUBJECT` (`template_todolist_subject_id`) ON DELETE CASCADE
);