# knowledge-base-search
A high-performance microservice for knowledge management and advanced full-text search. This project demonstrates a robust integration between a relational database (PostgreSQL) and a search engine (Elasticsearch) to provide near-instant search capabilities across complex datasets.

## Tech Stack
- Java 25:
- Spring Boot 4.0.1
- Spring Data JPA
- PostgreSQL
- Elasticsearch
- Docker

## Running the Application with Docker Compose

```bash
    docker-compose up --build
```

##API Documentation

- **POST `/api/v1/articles`**: Create a single article
- **POST `/api/v1/articles/bulk`**: Bulk upload multiple articles
- **GET `/api/v1/articles`**: all articles (Paginated)
- **GET `/api/v1/articles/search`**: Full-text search with dynamic filters (Paginated)
- **GET `/api/v1/articles/{id}`**: get an article by id
- **DELETE `/api/v1/articles/{id}`**: Remove article from both DB and ES
