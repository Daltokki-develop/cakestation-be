# Cake Station BE 🍰 🚏
## Tech Stack 🛠

```
- OS : Ubuntu 20.04
- Java Version : JDK 11   
- Framework : Spring boot 2.7.1
- ORM : Spring Data JPA
- Security : Spring Security
- Test : JUnit5, mockito, testcontainer, jacoco
- DB : MySQL
- Build : Gradle
- CI / CD : Github Actions 
- Infrastructure : AWS EC2, RDS, S3, Docker 
```

## Docker 🐳
**1. git repo clone**
```
git clone https://github.com/Daltokki-develop/cakestation-be.git
```
**2. 프로젝트 폴더로 이동**
```
cd cakestation-be
```
**3. 프로젝트 빌드**
```
./gradlew build
```
**4. 도커 이미지 빌드**
```
docker build -t cakestation-server .
```
**5. 도커 컨테이너 실행**
```
docker run -itd -p 8080:8080 -it --name cakestation-server cakestation-server
```


## Service Description

- BE 아키텍쳐
- 서비스 이미지 등

## Major Issues

- major Troubleshooting issues
