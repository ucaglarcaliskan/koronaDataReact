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
java -jar backend/target/korona-0.0.1-SNAPSHOT.jar
```

ya da farklı port ya da database ile çalıştırmak istenirse:

```
cd ..
java -jar backend/target/korona-0.0.1-SNAPSHOT.jar --spring.data.mongodb.uri=mongodb://localhost:27017/news
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
## Ekran Görüntüleri

<img src="https://github.com/user-attachments/assets/52acbe82-3df3-4359-b9d7-d2a6fc64f695" width="400" />
<img src="https://github.com/user-attachments/assets/f6a45f84-7297-4282-b873-15f5b7ce539b" width="400" />
<img src="https://github.com/user-attachments/assets/0060f5b6-fc84-49e8-95dd-e19e02f554a5" width="400" />


