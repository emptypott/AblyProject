# Fashion App Small Proejct 요약

# 🌿Project Structure

## Presenstation Layer

- View 에 해당하는 Activity / Fragment 를 User 와 소통하도록 구현.
- Presenter 에 해당하는 ViewModel 을 설정.

## Domain Layer

- 데이터를 저장 하고 수정하는 기능을 제공하는 **Repositry** 를 구현.

## Data Layer

- 실제 입출력이 이루어지는 Data source 를 구현하였습니다. 편의에 따라 Network (api), Database로 구분

# 🌿 주요 기능 소개

1. Rxjava 를 활용하여 시간마다 배너 이미지가 변경되도록 만들었습니다.
2. GlideApp 라이브러리를 활용하여 이미지가 변경되도록 설정하였습니다.
3.  ViewModel 을 공유함으로써 찜한 상품을 좋아요에서 확인할 수 있다.
4. Dagger2 라이브러리를 활용하여 객체를 재활용성을 증진, 커플링 방지 하도록 작업하였습니다.
5. Room 라이브러리를 활용하여, User 가 찜한 데이터를 내부 DB 로 저장하도록 구현하였습니다.
6. 상품이 10개씩 보여지도록 작업하였고, 추가적으로 데이터를 요청할 경우 하단에 추가되도록 작업하였습니다.
