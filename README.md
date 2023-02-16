# delivery-service

**주요 개발 환경**
- java 17  
- Spring Boot 
- Gradle 7.4


**요구 사항**
- 회원가입 기능
- 토큰 인증 기반 로그인 기능
- 사용자 배달 조회 기능
- 사용자 배달 도착지 정보 수정 기능

**요구 사항 상세 내용 정리**
- 배달 조회는 최대 3일 전까지 가능하다
  - 배달 조회는 주문 시각을 기준으로 한다
- 배달 수령지는 준비 상태에만 변경가능 하다
  - 배달 상태값은 준비, 배달기사배정, 시작, 완료의 상태를 가진다
- 하나의 배달은 하나의 주문을 가지고 있다 / 반대로, 하나의 주문은 하나의 배달을 가질수도, 가지지 않을 수도 있다
- 배달은 담당 기사, 수령지, 상태, 배송 메모 등 배달하기 위해 필요한 정보만을 가지고 있다
  - 나머지 주문에 대한 정보 등은 주문을 참조하여 알 수 있다


**논외**
- 주문에 대한 상세 기능
- 배달 상태 값을 변경하는 측(판매자?)의 모든 기능
- 배달 요금, 주문에 대한 비용 청구 등 기능
- 사용자 role 은 한 가지로 제한
  - 판매자, 배달 기사, 사용자 등의 구분이 필요할 것 같지만 논외로 한다
