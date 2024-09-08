# error-notifier
> 실시간 장애 알림 서비스


error-notifier는 **알림 대상 목록, 심각도, 장애 내용을 전달 받아 외부 서비스로 장애 알림 메시지를 전송**하기 위한 서비스입니다.

error-notifier는 **멀티 모듈 프로젝트**로, 다음 모듈로 구성됩니다.

- `common` : 모듈 공통 Utils, DTO 정의
- `producer` : 장애 알림 전송 요청을 받아 대상자를 식별하고, 외부 서비스 요청에 필요한 token, channelId를 조회하여 알림 전송 event 발행
- `consumer` : 알림 전송 event를 구독하여 외부 서비스 메시지 전송 API 호출
- `mockup` : 외부 서비스 mockup

<br>

---

## 실행 방법

Docker 실행 후 프로젝트 root 디렉토리에서 다음 명령어를 차례로 입력하여 실행할 수 있습니다.

```
./gradlew build

docker compose up
```

<br>

---

## 기술 스택

- Language : `Java 21 (LTS)`
- Framework : `Spring Boot 3.3.3`, `JUnit5`
- DB : `H2`, `Spring Data JPA`, `Flyway`
- ETC : `Kafka`, `Log4J2`, `OpenFeign`, `Docker`

<br>

---

## 시스템 아키텍처

<br>

---

## 소프트웨어 아키텍처

<br>

---

## DB 스키마

<br>

---

## API 명세

<br>

---

## 주요 라이브러리 및 오픈소스

<br>

---

