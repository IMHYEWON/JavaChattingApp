# JavaChattingApp
## 자바 소켓과 Swing UI를 이용한 채팅앱 예제

- Socket Programming
- Swing Ui

* * *

## Client
### Id 등록화면 (IdFrame) 
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff009e903-6ce7-45d4-8452-693805c0441e%2FUntitled.png?table=block&id=01ad8a28-8fec-48e8-a5c6-cc958af4fc12&spaceId=27adc556-0a9c-48ac-8c0e-7611b7df354c&width=1770&userId=1d8a34de-d3c7-4fa7-9b8d-0f3ce402b3d7&cache=v2" alt="IdFrame"></img></br>
- ID 등록 시 서버에 소켓과 함께 저장
- [등록] 버튼 클릭 후 IdFrame 닫고 ListFrame 보이도록 함
</br>

### 채팅 목록 화면 (ListFrame)
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fae7e9708-8934-4747-a106-13376040a84a%2FUntitled.png?table=block&id=27c9887e-6142-4c59-8fa7-b8d4fed24482&spaceId=27adc556-0a9c-48ac-8c0e-7611b7df354c&width=1770&userId=1d8a34de-d3c7-4fa7-9b8d-0f3ce402b3d7&cache=v2" alt="ListFrame"></img></br>
- 본인 제외한 다른 접속중인 사람들의 목록 조회
- **TitlePanel, ListPanel, btnPanel** 로 영역 구분
  - **TitlePanel** : *“Chat Lists”*
  - **ListPanel** : 로그인 후 클라이언트 리스트들을 받아서 동적으로 버튼 생성, 버튼 클릭 시 개인 채팅방 접속
  - **btnPanel** : *“Create New Chat”*으로 전체 채팅방 접속
</br>

### 채팅방 화면 (Client Frame)
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F79217f84-d25d-4ba2-ad00-75546fe4d6b2%2FUntitled.png?table=block&id=e77c8d48-74fc-4533-99f7-030847dca1f7&spaceId=27adc556-0a9c-48ac-8c0e-7611b7df354c&width=1770&userId=1d8a34de-d3c7-4fa7-9b8d-0f3ce402b3d7&cache=v2" alt="ClientFrame"> </img></br>

- **main**에서 **ClientFrame**에 접속 소켓정보와 클라이언트 목록 전달
- `titlePanel`, `scrPane`, `panel` 으로 영역 구분
    - **titlePanel** : 개인 채팅방 접속 시 대화하는 상대방의 이름 표시 *Private Chat with **[NAME]***
      전체 채팅방 접속 시 *Private Chat with Everybody* 표시
        
    - **ScrPane** : 스크롤 영역 (채팅 텍스트영역)
        - 보내는 메시지와 받는 메시지를 구분하기 위해서 TextArea 나누어서 생성
        보내는 메시지는 **파란 글씨**로 구분
        - 카카오톡같이 메시지 주고받는 것처럼 보이기 위해
        메시지 보낼 때는 받는 영역에 개행 추가, 메시지 받을 때는 보내는 영역에 개행 추가
    - **btnPanel** : 하단 메시지 **입력** 필드 및 **전송**, **나가기** 버튼 영역
        - 나가기 버튼 클릭 시 다른 채팅방에 ***‘[NAME] is out : ( … ’***메시지 전송

</br>

### ReadThread
- **새 클라이언트 접속 감지**
- 입력 메시지 **String 분기 처리로 Case 구분**
    - 새로운 클라이언트 접속
    - 개인 채팅
    - 전체 채팅


### WriteClass
- 첫번째 전송 = ID 입력인 경우 ID따로 전송함
- 클라이언트가 채팅방 나가기 버튼 클릭한 경우 Out  메시지 출력
- 그 외의 경우 String 전송
- WriteClass.java
