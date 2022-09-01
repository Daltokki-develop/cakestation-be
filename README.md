# Cake Station

## Environment

- java 버전 몇 이런거 쓰기

## Prerequisite

- Make a virtual environment

  ```
  $ cd cakestation-be
  $ 어쩌고COMMAND
  ```

- Run a virtual environment

  ```
  (myvenv) ~/cakestaion-be $ 어쩌고COMMAND
  ```

- Install requirements

  - install requirements

    ```
    (myvenv) ~$ pip install -r requirements.txt
    ```

  - pip upgrade (npm일수도)

    ```
    (myvenv) ~$ 어쩌고COMMAND
    ```
## Docker
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

## Usage

```
(myvenv) ~/cakestaion-be $ 어쩌고COMMAND
(myvenv) ~/cakestaion-be $ 어쩌고COMMAND
```



## Service Description

- BE 아키텍쳐
- 서비스 이미지 등

## Major Issues

- major Troubleshooting issues
