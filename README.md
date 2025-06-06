# HelloSpringDataJpa

Spring Boot와 Spring Security를 활용한 간단한 쇼핑몰 예제 프로젝트입니다.  
회원가입/로그인, 역할 기반 권한 관리, 상품 관리 등의 기능을 제공합니다.

---

## 목차

1. [프로젝트 소개](#프로젝트-소개)  
2. [주요 기능](#주요-기능)  
   - [1) 사용자 인증 기능](#1-사용자-인증-기능)  
   - [2) 권한(Role) 기반 인가 기능](#2-권한role-기반-인가-기능)  
   - [3) 추가 기능](#3-추가-기능) 
---

## 프로젝트 소개

`HelloSpringDataJpa`는 Spring Boot와 Spring Security를 기반으로 간단한 쇼핑몰 기능을 구현한 예제 애플리케이션입니다.  
회원가입부터 로그인/로그아웃, 관리자 페이지에서 사용자 관리, 상품 CRUD까지 기본적인 웹 애플리케이션 흐름을 연습해볼 수 있습니다.  

---

## 주요 기능

### 1) 사용자 인증 기능

- **회원가입 & 로그인 (이메일/비밀번호)**
  - `/helloSpringDataJpa` 접속 시, 로그인되지 않은 사용자는 자동으로 로그인 페이지로 리다이렉트됩니다.
  - 회원가입 시 이메일 형식 검증 및 빈 필드 체크를 수행하며, 잘못된 입력이 있을 경우 오류 메시지를 표시합니다.

- **Spring Security 기반 로그인/로그아웃**
  - `WebSecurityConfig.java`에서 `formLogin()`과 `logout()` 설정을 적용하여 `/login` POST 요청을 통해 인증을 수행합니다.
  - `home.html`에서 `sec:authorize` 태그를 활용하여 로그인 여부 및 역할에 따라 UI를 제어합니다.

- **BCryptPasswordEncoder 사용**
  - `RegistrationServiceImpl.java`에서 `BCryptPasswordEncoder`를 사용하여 입력된 비밀번호를 해시화하여 데이터베이스에 저장합니다.
  - 회원가입 후 저장된 비밀번호는 모두 암호화되어 안전하게 관리됩니다.

---

### 2) 권한(Role) 기반 인가 기능

- **권한(Role) 정의**
  - **ROLE_USER**: 일반 사용자 권한  
  - **ROLE_ADMIN**: 관리자 권한  

- **권한별 기능 제한**
  - **ROLE_USER**: 
    - 상품 목록 조회 (GET `/products`)  
  - **ROLE_ADMIN**: 
    - 상품 등록 (POST `/products`)  
    - 상품 수정 (PUT `/products/{id}`)  
    - 상품 삭제 (DELETE `/products/{id}`)  

- **UI 요소 제어**
  - `index.html` 및 `products.html` 등에서 `sec:authorize="hasRole('ROLE_ADMIN')"`를 활용하여,  
    - 일반 사용자에게는 상품 수정/삭제 버튼이 보이지 않도록 설정  
    - 관리자에게만 상품 등록 버튼을 노출  

- **신규 가입 시 기본 권한 설정**
  - `RegistrationController.java`에서 신규 가입 사용자는 기본적으로 `ROLE_USER`로 등록됩니다.  
  - 특정 이메일(`admin@hansung.ac.kr`)으로 가입한 경우, **관리자** 권한인 `ROLE_ADMIN`으로 설정할 수 있도록 로직을 구현했습니다.

---

### 3) 추가 기능

1. **로그인 성공/실패 시 사용자 맞춤 메시지 출력**  
   - 로그인 성공 시, 해당 사용자가 관리자/일반 사용자인지 확인하여 맞춤 환영 메시지를 출력합니다.  
   - 로그인 실패 시 “아이디와 비밀번호가 올바르지 않습니다.” 메시지를 표시합니다.  
   - 로그아웃 시에도 “정상적으로 로그아웃되었습니다.”와 같은 메시지를 출력합니다.  
   - `HomeController.java`와 `login.html`에서 HTTP 세션(`session.loginError` 등)을 활용하여 메시지를 처리합니다.

2. **상품 등록/수정 시 유효성 검사**  
   - `Product.java` 엔티티 클래스에 `@NotBlank`, `@Min(0)` 등의 어노테이션을 추가하여 필드별 검증을 수행합니다.  
   - `new_product.html` 및 `edit_product.html` 템플릿에서 각 입력 폼 아래에 검증 오류 메시지를 출력합니다.  
   - `ProductController.java`에서 `@Valid`와 `BindingResult`를 활용하여 서버 측 유효성 검사를 처리합니다.

3. **관리자 페이지 (ROLE_ADMIN 전용)**
   - 관리자 권한으로 로그인한 후, `/helloSpringDataJpa/home`의 관리자 영역에서 “사용자 관리” 메뉴를 클릭하면 `/helloSpringDataJpa/admin`으로 이동합니다.  
   - `/admin` 페이지에서는 가입된 모든 사용자를 리스트업하고, 개별 사용자를 삭제할 수 있는 기능을 제공합니다.
   - 해당 페이지 접근은 오직 `ROLE_ADMIN`에게만 허용됩니다.
