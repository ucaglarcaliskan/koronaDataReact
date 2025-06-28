# Korona Data Uygulaması

React, Spring Boot ve MongoDB kullanılarak geliştirilmiş basit bir veri takip uygulamasıdır.

## Gereksinimler

- Java 17
- Maven
- Node.js 18+
- MongoDB

## Kurulum ve Çalıştırma

### 1. Backend



target klasöründeki jar dosyasını çalıştırın:

```
cd ..
java -jar backend/target/backend.jar
```

ya da farklı port ya da database ile çalıştırmak istenirse:

```
cd ..
java -jar backend.jar --spring.data.mongodb.uri=mongodb://localhost:27017/news
```


Backend `http://localhost:8080` adresinde çalışır.

---

### 3. Frontend

Yeni bir terminal açıp frontend dizinine geçin:

```
cd frontend
npm install
npm run dev
```

Frontend `http://localhost:5173` adresinde çalışır.

---

## Notlar
Default mongodb konfigürasyonu:
```
spring.data.mongodb.uri=mongodb://localhost:27017/news
```

