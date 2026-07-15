# Database Setup

## Prerequisites

- MySQL 8.0 or later installed and running
- MySQL command-line client or MySQL Workbench

## Steps

1. Open a terminal and connect to MySQL:

```bash
mysql -u root -p
```

2. Run the schema script:

```sql
SOURCE path/to/database/library_schema.sql;
```

3. Load sample data (optional):

```sql
SOURCE path/to/database/sample_data.sql;
```

Or from command line:

```bash
mysql -u root -p < database/library_schema.sql
mysql -u root -p < database/sample_data.sql
```

## Verify

```sql
USE library_management;
SHOW TABLES;
SELECT COUNT(*) FROM books;
SELECT COUNT(*) FROM users;
```

## Configure Application

Update database credentials in `src/config/DBConnection.java` before running the application:

- `DB_URL` — default: `jdbc:mysql://localhost:3306/library_management`
- `DB_USER` — default: `root`
- `DB_PASSWORD` — your MySQL password
