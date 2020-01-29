# jasypt-spring-boot with JPA exam

Entity 자체에 `@TypeDef` 를 사용하는 방법이 [jasypt](http://www.jasypt.org/) 공식 페이지에 안내되고 있으나,  
여기서 사용하는 `jasypt-spring-boot` 에서는 해당 클래스 타입이 빠져있어서 사용이 안됨.  
구현하는 방법도 있을거같은데.. 거기까지 하기 귀찮고, @Converter 를 테스트 하는 겸 생성해 봤음.

---
spring-boot를 사용하고 있고, `@SpringBootApplication`을 사용하고 있기 때문에 jasypt-spring-boot-starter 의존성 추가를 한다.
```groovy
compile group: 'com.github.ulisesbocchio', name: 'jasypt-spring-boot-starter', version: '3.0.2'
```
현 시점(2020-01-28)에서 최신버전인 3.0.2를 추가함.\
[jasypt-spring-boot github](https://github.com/ulisesbocchio/jasypt-spring-boot) 에서 이야기 하듯이
`@EnableAutoConfiguration` 을 사용하지 않는다면 `jasypt-spring-boot`를 추가하자.

---
case 1,
`@Convert(converter=Converter.class)`를 이용한 암호화.\
DB에 입력되는 정보가 암호화가 되어야 할때, [jasypt](http://www.jasypt.org/hibernate.html)에서는 `@TypeDef`를 사용하라 하는데,\
정확히 말하면 내가 추가한 라이브러리는 `jasypt-spring-boot`이며, spring-boot와의 integration을 위한 라이브러리임.\
약간의 차이가 존재하긴 하는데, 하필이면 `EncryptedStringType`이나, `HibernatePBEStringEncryptor`를 지원하지 않으므로\
Entity자체의 `@Convert` annotation을 이용하기로 함. 사실 이게 맞게 하는건지 잘 모르겠음.\
Autoconfiguration을 이용하여 기본으로 생성된 빈을 이용하였으므로 별다른 설정이 필요없고, `@Autowired`로 바로 불러 사용하였음.

---
case 2,
위와 비슷한데, 기본으로 생성해준 빈이 아니라 필요에 의해서 자신이 명시적으로 생성한 빈을 이용함.\
암호화 방식이나 Encryptor 구현체를 바꿀수 있음. 대부분은 그냥 application.yml에서 설정으로 대체 가능함.\
Autoconfiguration이 그렇듯 이것도 빈을 따로 생성하면 빈을 자동으로 생성해주지 않음.\
컨버터를 용도별로 여러개 생성해야 할때 사용할수 있을거 같음.

---
case 3,
application.yml에 숨겨야 하는 값이 있을경우 사용하는 방법.\
구글에 그냥 검색하기만 해도 렛잇고 렛잇고 수준으로 나오는 부분이니 설명은 넘어가고\
다만 `jasypt-spring-boot` 버전에 따라서 기본 암호화 방식이나 IVGenerator 부분 디폴트 설정이 다르니 주의 할것.

---
oracle jdk 사용시 암호화 방식에서 Exception이 발생한다면 
[Java Cryptography Extension,JCE Unlimited Strength Jurisdiction Policy, java8](https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)에서 다운로드 후 
`<JAVA_HOME>/jre/lib/security/policy`에 넣어주고,\
자바8 업데이트151 이후부터는 `<JAVA_HOME>/jre/lib/security/java.security` 파일의 `crypto.policy=unlimited` 부분이 주석 처리 되어있는데, 해당부분을 주석해제 해주면 된다 함.\
