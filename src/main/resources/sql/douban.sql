
createUserTable = CREATE TABLE `user` (
                    `user_token` varchar(100) not NULL,
                    `location` varchar(255) DEFAULT NULL,
                    `business` varchar(255) DEFAULT NULL,
                    `sex` varchar(255) DEFAULT NULL,
                    `company` varchar(255) DEFAULT NULL,
                    `education` varchar(255) DEFAULT NULL,
                    `username` varchar(255) DEFAULT NULL,
                    `url` varchar(255) DEFAULT NULL,
                    `agrees` int(11) DEFAULT NULL,
                    `thanks` int(11) DEFAULT NULL,
                    `asks` int(11) DEFAULT NULL,
                    `answers` int(11) DEFAULT NULL,
                    `articles` int(11) DEFAULT NULL,
                    `followees` int(11) DEFAULT NULL,
                    `followers` int(11) DEFAULT NULL,
                    `userId` varchar(255) DEFAULT NULL,
                    PRIMARY KEY (`user_token`)
                    ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8

creatTokenTable = create table usertoken (
                    `user_token` varchar(100) not NULL,
                    PRIMARY KEY (`user_token`)
                    ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8