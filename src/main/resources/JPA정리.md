<h3>1. Entity와 DTO차이</h3>
> >Entity는 데이터베이스의 특정 테이블과 매핑되는 엔터티 클래스.
>>데이터베이스와 직접적으로 상호작용하며 주로 JPA의 @Entity 어노테이션을 사용하여 테이블을 정의한다.

>DTO는 데이터 전송을 위한 객체로 주로 컨트롤러와 뷰간의 데이터 전송에 사용된다. 데이터베이스의 Entity와는 직접적인 관련이 없다.

1-1. Annotation 및 기능:

    @Entity
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EntityListeners(value = { AuditingEntityListener.class })
    public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    @CreationTimestamp  //@CreatedDate = LocalDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp    //@LastModifiedDate = LocalDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

    public void change(String title, String content){
        this.title = title; // 매개변수로 받아온 title값으로 title정보를 갱신
        this.content = content; // 매개변수로 받아온 content값으로 content 정보를 갱신
    }
>@Entity는 해당 클래스를 JPA 엔터티로 지정. JPA는 이 어노테이션이 붙은 클래스를 데이터베이스 테이블과 매핑.
> @Getter,@Setter,@ToString은 Lombok 라이브러리에서 제공하는 어노테이션.
>>@Getter,@Setter는 자동의 getter,setter메서드를 생성한다. 
>>@ToString은 자동의 toString()메서드를 생성한다.
>>이거말고 @Data 어노테이션이 위 3개와 @EqualsAndHashCode, @RequiredArgsConstructor를 한번에 적용한다. 
> 
>@Builder는 Lombok에서 제공하는 어노테이션으로 빌더 패턴 적용. 빌더패턴은 매개변수의 순서를 신경쓰지 않고도 객체를 생성할수 있다.
>>import lombok.Builder;<br>
import lombok.Getter;<br>
import lombok.ToString;<br>
@Getter<br>
@Builder<br>
@ToString<br>
public class Person {<br>
private String firstName;<br>
private String lastName;<br>
private int age;<br>
    // 기본 생성자나 다른 생성자를 직접 작성할 필요가 없음<br>
}<br>
// 사용 예시<br>
public class BuilderExample {<br>
public static void main(String[] args) {<br>
Person person1 = Person.builder()<br>
.firstName("John")<br>
.lastName("Doe")<br>
.age(30)<br>
.build();<br>
        System.out.println(person);<br>
    }<br>}

>@NoArgsConstructor, @AllArgsConstructor는 Lombok 어노테이션으로 각각 파라미터가 없는 기본생성자와 모든 필드를 파라미터로 받는 생성자를 자동으로 생성한다.<br>
>@EntityListeners(value={AuditingEntiryListener.class})는 JPA AUditing을 활성화하기 위한 어노테이션. Auditing은 엔터티의 생성 및 수정 시간을 자동으로 관리하는 기능 제공
> @Id는 해당 필드를 엔터티의 primarykey로 지정<br>
> @GeneratedValue(strategy=GenerationType.IDENTITY)는 주키의 생성전략을 지정. 데이터베이스의 identity 컬럼을 사용하여 자동으로 생성되도록 설정함.<br>
> @CreationTimestamp, @UpdateTimestamp는 JPA Auditing을 사용할때, 엔터티의 생성 시간('regdate')및 수정 시간('moddate')를 자동으로 관리하기 위한 어노테이션이다
>> @CreationTimestamp:엔터티가 생성될때 자동으로 타임스탬프를 설정한다.
>>@UpdateTimestamp는 엔터티가 수정될때 자동으로 타임스탬프를 갱신한다.
>
>@Column은 데이터베이스 테이블의 컬럼에 대한 설정을 지정.
> 
 