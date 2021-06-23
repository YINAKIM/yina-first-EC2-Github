package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/***********************************************************************************
//// Entity 클래스에서는 절대 Setter메서드를 만들지 않는다 ////

 ---> setter 대신 필드값 변경이 필요하면 명확히 목적, 의도를 나타낼 수 있는 메소드를 추가한다.

 setter없이 DB에 값은 어떻게 넣지?
 1. 생성자를 통해 최종값을 채운다. -> DB에 insert
 2. 값 변경이 필요한 경우, [해당 이벤트에 맞는 public메소드]를 호출하여 변경한다.

 그래서 @Builder 로 제공되는 빌더클래스를 사용한다.
 생성자 = 빌더 = 생성시점에 값을 채워주는 역할
 단, 생성자는 지금 채워야할 필드가 뭔지 명확히 지정할 수 없다 BUT 빌더를 사용하게 되면 어느 필드에 어떤ㄱ밧을 채워야할지 명확하게 인지할 수 있다. ( 네이버api 소스에서 했던것 )
***********************************************************************************/
@Getter //lombok
@NoArgsConstructor // lombok
@Entity //JPA어노테이션, 테이블과 링크될 클래스, 기본값으로 카멜케이스이름을 언더스코어 네이밍으로 테이블이름 매칭함
public class Posts extends BaseTimeEntity {
    //(주요어노테이션을 코드와 가장 가깝게)
    // 실제 DB테이블과 매칭될 클래스
    // (Entity 클래스 : DB데이터에 할 작업을 실제 쿼리를 날리는 것이 아니라 여기서 작업한다.)

    @Id //해당테이블의 PK필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK의 생성규칙
                                                         // (스프링부트2.0에서는 GenerationType.IDENTITY옵션을 추가해야만 auto_increment됨
    /*
    * @Column을 굳이 선언하지 않더라도 여기 클래스의 필드는 모두 컬럼이 된다.
    * 왜사용? 기본값 외에 추가로 변경이 필요한 옵션이 있다면 사용
    * ex1) 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘린다거나 : String title의 속성지정 length=500
    * ex2) 데이터타입을 TEXT로 변경하고싶다거나 : String content의 속성지정 columnDefinition = "TEXT"
    * */

    private Long id; // Entity클래스의 PK는 Long타입 Auto_increment 사용
                     // 인덱싱에 장점, 만약 이렇게 안하고 필드 여러개로 (복합키로) PK를 잡으면? FK맺을 때 남감하거나 유니크한 조건이 변경되었을 때 난감

    @Column(length = 500, nullable = false) //테이블의 column을 나타냄,
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
