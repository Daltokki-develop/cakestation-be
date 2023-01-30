# Cake Station BE 🍰 🚏

## Service Description 👀
***지하철 역 위치 기반 레터링 케이크 리뷰 플랫폼***   

- 지도를 확인하며 원하는 케이크 가게의 위치를 확인할 수 있어요 🗺
- 지하철 역을 기반으로 가까운 케이크 가게를 검색할 수 있어요 🚉
- 방문했던 케이크 가게에 별점과 리뷰를 남길 수 있어요 ⭐️
- 좋아요 기능을 통해 좋았던 가게들을 저장해둘 수 있어요 ❤️
- 마이 페이지 기능을 통해 활동 기록을 볼 수 있어요 🏃🏻‍♀️


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


## Major Issues

### 쿼리 성능 관련 이슈
#### 리뷰 삭제 쿼리 성능 문제
하나의 리뷰를 삭제할 때 연관된 리뷰 이미지(N개)와 리뷰 태그(M개)를 cascade로 한번에 삭제하고자 하였다. (리뷰 이미지와 리뷰 태그는 오로지 리뷰와의 연관성만 있기에 한번에 삭제해도 괜찮다고 생각하였다.)   
하지만 이렇게 할 경우 1:N 관계에서 1을 삭제할 때 N번(리뷰 이미지,태그 개수 만큼)의 삭제 쿼리가 날라가게 되는데 이게 성능상의 문제가 될 것이라는 생각이 들었다. 
그래서 bulk delete로 직접 쿼리를 생성해 한번의 쿼리로 대량 데이터 삭제가 가능하도록 변경해주었다. 

```java
    @Modifying(clearAutomatically = true)
    @Query("delete from ReviewImage ri where ri.review.id in :ids")
    void deleteReviewImagesByReviewIds(@Param("ids") List<Long> ids);
```

- **벌크 연산 쿼리는 1차 캐시를 포함한 영속성 컨텍스트를 무시하고 바로 query를 실행하기 때문에** 영속성 컨텍스트는 데이터 변경을 알 수 없다. 따라서 `@Modifying` 어노테이션을 추가해주어야 한다. 
- 또한 `@Modifying`을 사용할때 `clearAutomatically=true` 속성을 주어, 연산 직 후 영속성 컨텍스트를 반드시 초기화해 데이터를 동기화해주어야 한다.   
- 더 나아가, 가게 삭제시 삭제할 대용량 리뷰들과 관련된 정보들 삭제에도 적용해주었다.

### 테스트 관련 이슈
#### 1. 컨트롤러 테스트 중, MockMvc 응답 시 한글 깨짐 문제   
테스트 중 한글을 사용하면 한글이 깨져 테스트에 실패하는 문제를 겪었다. mockMvc 설정 시 UTF-8 인코딩 필터를 추가해주어 문제를 해결할 수 있었다.   
```java
    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }
```
</br>

#### 2. @WebMvcTest를 사용한 슬라이스 테스트에서 ```JPA metamodel must not be empty!``` 에러 발생 문제
WebMvcTest 의 경우 웹과 관련된 의존성만 주입받는다. 따라서 컨트롤러 슬라이스 테스트를 할 때 주로 사용된다.   
해당 문제는 **메인클래스**에 등록되어 있는 ```@EnableJpaAuditing``` 과 관련된 JPA 관련된 주입이 없어 나타나는 문제였다. 
통합테스트였다면 모든 빈들을 주입받기 때문에 상관없었겠지만 ```@WebMvcTest``` 에서는 나타날 수 있는 문제였다.
```java
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
```
config 패키지에 따로 JpaAuditingConfig 클래스를 만들어 분리하여 관리해줌으로써 문제를 해결할 수 있었다.

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