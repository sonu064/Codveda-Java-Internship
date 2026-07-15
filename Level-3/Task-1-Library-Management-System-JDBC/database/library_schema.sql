-- Library Management System - Database Schema
-- Database: library_management

CREATE DATABASE IF NOT EXISTS library_management;
USE library_management;

-- Books table
CREATE TABLE IF NOT EXISTS books (
    book_id            INT AUTO_INCREMENT PRIMARY KEY,
    title              VARCHAR(200) NOT NULL,
    author             VARCHAR(150) NOT NULL,
    category           VARCHAR(100) NOT NULL,
    isbn               VARCHAR(20)  NOT NULL UNIQUE,
    quantity           INT          NOT NULL DEFAULT 1,
    available_quantity INT          NOT NULL DEFAULT 1,
    CONSTRAINT chk_quantity_positive CHECK (quantity >= 0),
    CONSTRAINT chk_available_valid CHECK (available_quantity >= 0 AND available_quantity <= quantity)
);

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id    INT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(150) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    phone      VARCHAR(15)  NOT NULL
);

-- Borrow transactions table
CREATE TABLE IF NOT EXISTS borrow_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT          NOT NULL,
    book_id        INT          NOT NULL,
    borrow_date    DATE         NOT NULL,
    return_date    DATE         NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'BORROWED',
    CONSTRAINT fk_borrow_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_borrow_book FOREIGN KEY (book_id) REFERENCES books(book_id),
    CONSTRAINT chk_status CHECK (status IN ('BORROWED', 'RETURNED'))
);

-- Indexes for common queries
CREATE INDEX idx_books_title    ON books(title);
CREATE INDEX idx_books_author   ON books(author);
CREATE INDEX idx_books_category ON books(category);
CREATE INDEX idx_borrow_user    ON borrow_transactions(user_id);
CREATE INDEX idx_borrow_book    ON borrow_transactions(book_id);
CREATE INDEX idx_borrow_status  ON borrow_transactions(status);
