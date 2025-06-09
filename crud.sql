-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 09, 2025 at 03:16 PM
-- Server version: 10.11.13-MariaDB-0ubuntu0.24.04.1
-- PHP Version: 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `crud`
--

-- --------------------------------------------------------

--
-- Table structure for table `history_logs`
--

CREATE TABLE `history_logs` (
  `id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `supplier_name` varchar(100) NOT NULL,
  `product_id` int(11) NOT NULL,
  `operation` varchar(50) NOT NULL,
  `at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `history_logs`
--

INSERT INTO `history_logs` (`id`, `product_name`, `supplier_name`, `product_id`, `operation`, `at`) VALUES
(1, 'new product', 'lex', 21, 'Added', '2025-06-06'),
(2, 'e5540', 'dell', 1, 'Added', '2025-06-06'),
(3, 'e5540', 'dell', 1, 'Updated', '2025-06-06'),
(4, 'e5540', 'dell', 1, 'Updated', '2025-06-06'),
(5, 'kdsf', 'ksajdfh', 22, 'Added', '2025-06-06'),
(6, 'kdsf', 'ksajdfh', 0, 'Deleted', '2025-06-06'),
(7, 'new product', 'lex', 0, 'Deleted', '2025-06-06'),
(8, 'lx500', 'Lenovo', 0, 'Deleted', '2025-06-06'),
(9, 'new ', 'hi', 0, 'Deleted', '2025-06-06'),
(10, 'r3', 'Aquas', 6, 'Updated', '2025-06-06'),
(11, 'new product', 'tested', 0, 'Deleted', '2025-06-06'),
(12, 'r3', 'Aquas', 6, 'Updated', '2025-06-07'),
(13, 'e5540', 'dell', 1, 'Reordered', '2025-06-08'),
(14, 'e5540', 'dell', 1, 'Updated', '2025-06-08'),
(15, 'e5540', 'dell', 1, 'Reordered', '2025-06-08'),
(16, 'e5540', 'dell', 1, 'Reordered', '2025-06-08'),
(17, 'e5540', 'dell', 1, 'Updated', '2025-06-08'),
(18, 'e5540', 'dell', 1, 'Reordered', '2025-06-08'),
(19, 'e5540', 'dell', 1, 'Updated', '2025-06-08'),
(20, 'e5540', 'dell', 1, 'Reordered', '2025-06-08'),
(21, 'v40', 'Vivo', 4, 'Reordered', '2025-06-08'),
(22, 'e5540', 'dell', 1, 'Updated', '2025-06-08'),
(23, 'e5540', 'dell', 1, 'Updated', '2025-06-08'),
(24, 'Thinkpad x200', 'Lenovo', 15, 'Reordered', '2025-06-08'),
(25, 'e5540', 'dell', 1, 'Reordered', '2025-06-08'),
(26, 'e5540', 'dell', 1, 'Reordered', '2025-06-08');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `total_amount` decimal(10,0) NOT NULL,
  `added_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `category`, `quantity`, `supplier_id`, `price`, `total_amount`, `added_at`) VALUES
(1, 'e5540', 'laptop', 25, 2, 10, 250, '2025-05-23'),
(4, 'v40', 'Smart Phone', 65, 13, 1000, 65000, '2025-05-25'),
(6, 'r3', 'Smart Phone', 30, 24, 1200, 36000, '2025-05-25'),
(7, 'abc', 'DEF', 55, 16, 1000, 55000, '2025-05-25'),
(8, 'jordan 4', 'shoes', 20, 19, 1230, 24600, '2025-05-25'),
(10, 'thinkpad 3.5', 'Laptop', 20, 21, 1200, 13200, '2025-05-25'),
(13, 'air dunks', 'shoes', 600, 19, 1000, 600000, '2025-05-27'),
(15, 'Thinkpad x200', 'Laptop', 105, 21, 12400, 1302000, '2025-05-28'),
(18, 'lx355', 'charger', 100, 2, 1000, 100000, '2025-06-06');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`id`, `name`, `email`) VALUES
(1, 'nike', 'nike@kuss.com'),
(2, 'dell', 'products@dell.com'),
(3, 'DELL', 'D'),
(4, 'rjk', 'tejkq'),
(5, 'abc', 'cb'),
(6, 'abc', 'bcbbc'),
(7, 'aabc', 'cbbajdfkj'),
(8, 'aaaaa', 'aaaa'),
(9, 'KS', 'DFSD'),
(10, 'jkdsf', 'sdfakj'),
(11, 'Vivo', 'products@vivo.com'),
(12, 'aa', 'aaa'),
(13, 'Vivo', 'vivo@vivo.com'),
(14, 'Aqua', 'aqua@shark.com'),
(15, 'fd', 'fdd'),
(16, 'gh', 'ghhh'),
(17, 'Aquas', 'aquas@shark.com'),
(18, 'Aqua', 'aquas@shark.com'),
(19, 'nike', 'products@nike.com'),
(20, 'hi', 'hi'),
(21, 'Lenovo', 'products@lenovo.com'),
(22, 'lll', 'lll'),
(23, 'kj', 'jdsf'),
(24, 'Aquas', 'elelel@gmzi.com'),
(25, 'sony', 'products@sony.com'),
(26, 'nothing', 'products@nothing.com'),
(27, 'kjgajkl', 'kjgja'),
(28, 'tested', 'tested@gmail.com'),
(29, 'lex', 'lex@lex.com'),
(30, 'ksajdfh', 'KASDHF');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `verified` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `verified`) VALUES
(33, 'Mudasser Khan', 'kmudasser44@gmail.com', '$2a$10$icPms.8TzNOg7ObXbfUfs.ZieSEhowabAN3LLyNGp56wCkT9TAEl6', 1),
(35, 'admin', 'admin@super', '$2a$10$2S4iiG4MF8cSUv493CVP6e.cOm3zfSL1y9/qExAvTQ13aF8ggz7jm', 1),
(37, 'zainab', 'zaini@gmail.com', '$2a$10$uEYEFRS39scsjDIk.7eefugDW1XII3sf9F1KbXIhnHctLE4YHwlQq', 0),
(39, 'Mudasser Khan', 'dummy@gmail.com', '$2a$10$cph/2RP5EKWgePjWwiAV9uX6FSEtJo98UOI7Ov67MtZPurkO/RhMu', 0),
(40, 'Zainab', 'zaini@mail.com', '$2a$10$vrWEr6eq6.cmZc65vgkxuOda845dElyeTsJeFAM.1ckAwzW1omrQO', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `history_logs`
--
ALTER TABLE `history_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `history_logs`
--
ALTER TABLE `history_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
