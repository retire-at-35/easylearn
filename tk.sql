-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tk
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT 'https://web-tlias-yaojiongdong.oss-cn-guangzhou.aliyuncs.com/137de07c-aef2-4582-a775-8543a1db3009.jpg',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1,'lisi','e10adc3949ba59abbe56e057f20f883e','https://web-tlias-yaojiongdong.oss-cn-guangzhou.aliyuncs.com/935811d0-7fda-49f5-b76b-7ce89b49187a.png'),(2,'wangwu','e10adc3949ba59abbe56e057f20f883e','https://web-tlias-yaojiongdong.oss-cn-guangzhou.aliyuncs.com/c534b261-530a-433a-95cb-2c09d055f653.jpg');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chapter` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `chapter_name` varchar(200) DEFAULT NULL,
  `pos` int DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
INSERT INTO `chapter` VALUES (1,'第1章 HTML基础',1),(2,'第2章 CSS基础',2),(3,'第3章 JavaScript',3);
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `oid` int NOT NULL AUTO_INCREMENT,
  `qid` int DEFAULT NULL,
  `op` varchar(200) DEFAULT NULL,
  `pos` int DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (19,12,'良法善治',1),(20,12,'依宪治国',2),(21,12,'依宪执政',3),(22,12,'依法执政',4),(23,13,'宪法',1),(24,13,'法律',2),(25,13,'法规 ',3),(26,13,'国家政策',4),(27,14,'社会主义核心价值观 ',1),(28,14,'弘扬中国传统文化 ',2),(29,14,'社会主义道德文化建设 ',3),(30,14,'社会主义精神文明建设',4),(31,15,'法制 ',1),(32,15,'法治',2),(33,15,'特色 ',3),(34,15,'现代化',4),(35,16,'五年 ',1),(36,16,'三年 ',2),(37,16,'二年 ',3),(38,16,'一年',4),(39,17,'亲属',1),(40,17,'债务人',2),(41,17,'债权人',3),(42,17,'利害关系人',4),(43,18,'个人财产',1),(44,18,'家庭财产',2),(45,18,'夫妻财产 ',3),(46,18,'共同财产',4),(47,19,'债务人',1),(48,19,'第三人 ',2),(49,19,'当事人 ',3),(50,19,'债权人',4),(51,20,'法律地位',1),(52,20,'法律责任',2),(53,20,'法定权利 ',3),(54,20,'法定义务',4),(55,21,'社会主义',1),(56,21,'优秀',2),(57,21,'中华优秀传统 ',3),(58,21,'民族',4),(131,40,'A、法律、法规',1),(132,40,' B、法律 ',2),(133,40,'C、法规 ',3),(134,40,'D、政策',4),(135,41,'A、理想教育',1),(136,41,' B、道德教育 ',2),(137,41,'C、文化教育 ',3),(138,41,'D、纪律和法制教育',4),(139,42,'A、政府',1),(140,42,' B、社会 ',2),(141,42,'C、群众 ',3),(142,42,'D、协会',4),(143,43,'A、公司职工',1),(144,43,' B、消费者 ',2),(145,43,'C、生态环境保护',3),(146,43,' D、股东',4),(147,44,'A、司法机关',1),(148,44,' B、仲裁机构',2),(149,44,' C、法律援助机构',3),(150,44,' D、政府部 门',4),(151,45,'A、国家利益 ',1),(152,45,'B、社会公共利益',2),(153,45,' C、他人合法权益',3),(154,45,' D、第三方利益',4),(167,50,'正确',1),(168,50,'错误',2),(169,51,'正确',1),(170,51,'错误',2),(171,52,'正确',1),(172,52,'错误',2),(173,53,'正确',1),(174,53,'错误',2),(233,65,'123',1),(234,65,'123',2),(235,65,'123',3),(236,65,'123',4);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `qid` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `sid` int DEFAULT NULL,
  `tid` int DEFAULT NULL,
  `content` varchar(250) DEFAULT NULL,
  `key` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (12,1,1,1,'___是依法治国的关键。','B'),(13,1,1,1,'坚持依法治国，推进法治中国建设要完善以___为核心的中国 特色社会主义法律体系','A'),(14,1,1,1,'根据《中华人民共和国宪法》规定，国家倡导___，提倡爱祖 国、爱人民、爱劳动、爱科学、爱社会主义的公德。','A'),(15,1,1,1,'根据《中华人民共和国宪法》规定，中华人民共和国实行依 法治国，建设社会主义___国家。','B'),(16,1,2,1,'根据《中华人民共和国民法典》规定，向人民法院请求保护 民事权利的诉讼时效期间为___。法律另有规定的，依照其规定。','B'),(17,1,2,1,'根据《中华人民共和国民法典》规定，财产代管人不履行代管职责、侵害失踪人财产权益或者丧失代管能力的，失踪人的___可 以向人民法院申请变更财产代管人。','D'),(18,1,2,1,'根据《中华人民共和国民法典》规定，个体工商户的债务， 个人经营的，以个人财产承担；家庭经营的，以家庭财产承担；无法 区分的，以___承担。','B'),(19,1,2,1,'根据《中华人民共和国民法典》规定，依法成立的合同，仅 对___具有法律约束力，但是法律另有规定的除外。','C'),(20,1,2,1,'根据《中华人民共和国民法典》规定，民事主体在民事活动 中的___一律平等。','A'),(21,1,2,1,'、根据《中华人民共和国爱国主义教育法》规定，爱国主义教 育应当坚持传承和发展___文化，弘扬社会主义核心价值观，推进中 国特色社会主义文化建设，坚定文化自信，建设中华民族现代文明。','C'),(40,1,1,1,'根据《中华人民共和国行政复议法》规定，行政复议机关办 理行政复议案件，可以进行调解。调解应当遵循合法、自愿的原则， 不得损害国家利益、社会公共利益和他人合法权益，不得违反___的 强制性规定。','A'),(41,1,1,2,'根据《中华人民共和国宪法》规定，国家通过普及___、___、 ___、___，通过在城乡不同范围的群众中制定和执行各种守则、公约， 加强社会主义精神文明的建设。','ABCD'),(42,1,2,2,'根据《中华人民共和国民法典》规定，营利法人从事经营活 动，应当遵守商业道德，维护交易安全，接受___和___的监督，承担 社会责任。','AB'),(43,1,2,2,'新修订的《中华人民共和国公司法》规定，公司从事经营活 动，应当充分考虑___、___等利益相关者的利益以及___等社会公共 利益，承担社会责任。','ABC'),(44,1,2,2,'根据《中华人民共和国无障碍环境建设法》规定，___、___、 ___应当依法为残疾人、老年人参加诉讼、仲裁活动和获得法律援助 提供无障碍服务。','ABC'),(45,1,2,2,'根据《中华人民共和国民事诉讼法》规定，当事人之间恶意 串通，企图通过诉讼、调解等方式侵害___、___或者___，人民法院 应当驳回其请求，并根据情节轻重予以罚款、拘留。','ABC'),(50,1,1,3,'新修订的《中华人民共和国公司法》规定，公司股东会、董 事会、监事会召开会议和表决可以采用电子通信方式，公司章程另有 规定的除外。','A'),(51,1,1,3,'国家机关、居民委员会、村民委员会、密切接触未成年人的 单位及其工作人员，在工作中发现未成年人身心健康受到侵害、疑似 受到侵害或者面临其他危险情形的，应当立即向公安、民政、教育等 有关部门报告。','A'),(52,1,2,3,'制定或者修改涉及无障碍环境建设的法律、法规、规章、规 划和其他规范性文件，应当征求残疾人、老年人代表以及残疾人联合 会、老龄协会等组织的意见。','A'),(53,1,2,3,'根据《中华人民共和国民事诉讼法》规定，非经当事人同意， 民事诉讼活动可以通过信息网络平台在线进行。民事诉讼活动通过信 息网络平台在线进行的，与线下诉讼活动具有同等法律效力。','B');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `roleName` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (5,5,'ROLE_superadmin'),(7,10,'ROLE_admin'),(8,11,'ROLE_admin'),(9,12,'ROLE_admin'),(10,13,'ROLE_admin'),(11,14,'ROLE_admin'),(12,15,'ROLE_admin'),(13,17,'ROLE_admin'),(17,22,'ROLE_admin'),(18,23,'ROLE_admin'),(19,24,'ROLE_admin');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `section` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `cid` int DEFAULT NULL,
  `section_name` varchar(200) DEFAULT NULL,
  `pos` int DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
INSERT INTO `section` VALUES (1,1,'第1节 认识HTML标签',1),(2,1,'第2节 属性',2),(3,1,'第3节 格式化文本',3),(4,1,'第4节 表格与表单',4),(5,2,'第1节 CSS选择器',1),(6,2,'第2节 组合选择器',2),(7,2,'第3节 盒子模型和文本样式',3),(8,2,'第4节 定位和浮动',4),(9,2,'第5节 页面布局',5),(10,2,'第6节 CSS3新特性',6),(11,3,'第1节 基础语法',1),(12,3,'第2节 程序结构',2),(13,3,'第3节 DOM',3),(14,3,'第4节 BOM',4),(15,1,'图片与视频',5),(16,1,'文件上传',6);
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tmtype`
--

DROP TABLE IF EXISTS `tmtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tmtype` (
  `tid` int NOT NULL AUTO_INCREMENT,
  `tmtype_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tmtype`
--

LOCK TABLES `tmtype` WRITE;
/*!40000 ALTER TABLE `tmtype` DISABLE KEYS */;
INSERT INTO `tmtype` VALUES (1,'单选题'),(2,'多选题'),(3,'判断题');
/*!40000 ALTER TABLE `tmtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'jjjj','$2a$10$U50JT5qrD3xAR0wVBsgQgeJQA7FXnRpcffOA7.OpNd/D1H4se0vi2'),(10,'yyyy','$2a$10$P37reNmeYEakng94T1NE0OhGyS0N/RhuQ4rSmgU9BqOdP3wdo1VF2'),(11,'oooo','$2a$10$r4Ua5ShAZWoqHTe1rc3MNuhrz/iQpMPAm2sCsjBhXhySlUYD/AyhK'),(12,'qqqq','$2a$10$hee8c.oyfAmH2DzBVyKUcenHRmKocWsY2Vd0AHNl8Ve7LudG6wiLC'),(13,'yaojiongdong','$2a$10$By9q20t5ICZkPXYDXRjMRub1UA6Oh2PoL/8KlnB54lQmvjBd9JxxO'),(14,'asdf','$2a$10$SiTkJ1inbCm3iiMjfAAR2ugeXY0ZFjb12Lt6YzsdwWyOS8LiJxcH2'),(15,'jjjj1','$2a$10$PAHis13HjC/NKEfMrYH6juwn/cEbXNQnqVQlT1Kv.yfq49wCawVYS'),(17,'j234','$2a$10$u5I9eW0JjgVDGg8aIHMMTOUcFrqSYk3PpCe2zwvxWalF50VJnzdsC'),(22,'18688886666','$2a$10$WA6TMwFK3ofSTJsAqF/uf.Y.JulL/K.zhREuevjNp6s57cvVj7OkS'),(23,'cccc','$2a$10$bQn6SDJbdHxEvx3fd3wK4uhOKpgbIT7ax76ENpYexW.C6TtELfCIq'),(24,'kalsdjfl;','$2a$10$HS.x3zE9aXJg2ofdota8VOt57UZvuyJIfdNCALCJBe4CM/41i1YOy');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26 21:48:02
