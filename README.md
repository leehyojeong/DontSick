## DontSick

### 프로젝트 배경
- 어떤 질병인지 잘 모르는 경우 증상을 간단하게 입력하면 질병에 대한 정보를 알려준다.
- 질병에 관한 정보는 알고 있지만 어떤 병원에 가야하는지 모르는 경우 해당 질병과 관련된 병원을 사용자 근처에서 찾아준다.
- 질병, 질환에 대한 응급처치 등 간단한 대처 방법을 알려준다.

![image](https://user-images.githubusercontent.com/39904216/90333424-92753c80-e000-11ea-8151-db92af41d57c.png)

### 컴포넌트 설명
- MainActivity : 로그인 화면
  - Firebase에 회원정보를 저장하고 전화번호를 입력하여 로그인할 수 있도록 만들었다. 
  - 로그인 버튼을 누르면 user collection에서 사용자가 입력한 정보가 있는지 확인 후 DB에 저장되어 있는지 확인한다.
  - 회원가입 버튼을 누르면 JoinActivity로 전환한다.
  - 비회원 버튼을 누르면 SearchAcitivy로 전환하여 바로 증상 부위를 선택할 수 있는 화면으로 넘어간다.
- JoinActivity : 회원가입 화면
  - 사용자의 이름을 아이디로 사용하고 전화번호를 패스워드로 사용한다.
  - 가입 버튼을 누르면 사용자가 입력한 정보가 DB에 있는지 확인하고 없는 경우에만 등록한다.
- SearchActivity : 증상 부위 선택 화면
  - 체형도를 주고 증상이 있는 부위를 터치하면 상세 부위를 선택할 수 있다. 
  - 세부 선택까지 완료하면 증상을 검색할 수 있다.
  - DB에서 해당 부위에서 발생할 수 있는 증상을 기반으로 관련 질병을 알려준다.
- SearchDiseaseActivity : 병명으로 검색
  - 증상 부위 선택 대신 바로 병명으로 검색할 수 있는 화면이다.
  - 전체 병원 collection에서 사용자가 입력한 값과 일치하는 병명이 있는지 비교한 뒤 있을 경우 해당 질병에 대한 정보를 보여준다.
- SymptomActivity : 증상으로 검색
  - 증상 부위 선택 대신 바로 증상으로 검색할 수 있는 화면이다.
- MapActivity : 병원 위치 정보 화면
  - SearchActivity, SearchDiseaseActivity, SymptomActivity에거 검색한 후 해당 질병 또는 병원을 선택했을 때 지도에 관련 병원 위치를 마커로 표시한다.
  - 병원 정보는 XmlpullParser로 xml api를 파싱하여 사용하였다.
    - 출처 : https://namu.wiki/w/%EC%A7%88%EB%B3%91/%EB%AA%A9%EB%A1%9D
    - 출처 : 서울대학교병원 질병 관련 정보

### 실행 화면

![image](https://user-images.githubusercontent.com/39904216/90333754-555e7980-e003-11ea-82e2-00d911516a97.png)


  
