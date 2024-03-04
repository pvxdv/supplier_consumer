## Запуск контейнера PostgreSQL

```
docker run --name supplier_consumer_bd_container -p 5432:5432 -e POSTGRES_USER=sc -e POSTGRES_PASSWORD=password -e POSTGRES_DB=supplier-consumer-db -d postgres:latest
```

- `--name supplier_consumer_bd_container` – имя контейнера.
- `-p 5432:5432` – порт контейнера : порт внутри контейнера.
- `-e` – установить переменные окружения.
- `-e POSTGRES_USER=sc` – имя супер юзера.
- `-e POSTGRES_PASSWORD=password` – пароль супер юзера.
- `-e POSTGRES_DB=supplier-consumer-db` – имя бд.
- `-d` – запустить контейнер в фоновом режиме.
- `postgres:latest` – последняя версия бд.
