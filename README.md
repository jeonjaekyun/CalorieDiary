# CalorieDiary
## 주제

칼로리 섭취량 기록 app

## 주제 선정 이유

Andorid를 공부하던 중 프로젝트를 만들어보고 싶어 평소 관심있던 운동과 관련된 칼로리 섭취 App을 주제로 선정하였다. 

## 개발 목적

사용자가 칼로리 섭취량을 기록하여 자신의 식단을 확인하고 관리한다

## 구조도

![CalorieDiary_Structure_Chart](https://user-images.githubusercontent.com/39545165/58540269-fc56ca00-8233-11e9-9ef3-7eaec93c4e25.jpg)

## 스크린샷

#### 시작 / 개인 정보 입력 / 메인

<div>
  <img width="200" src="https://user-images.githubusercontent.com/39545165/58540556-91f25980-8234-11e9-9ed8-161c30db8793.png">
  <img width="200" src="https://user-images.githubusercontent.com/39545165/58540557-91f25980-8234-11e9-8e2b-cb2c66ac8321.png">
  <img width="200" src="https://user-images.githubusercontent.com/39545165/58540559-91f25980-8234-11e9-9315-462f1f27f8a6.png">
</div>

#### 음식 검색 / 끼니 별 섭취 리스트/ 일별 식단 기록

<div>
  <img width="200" src="https://user-images.githubusercontent.com/39545165/58540560-928af000-8234-11e9-9dae-d7085bb84b2b.png">
  <img width="200" src="https://user-images.githubusercontent.com/39545165/58540561-928af000-8234-11e9-9efc-80f6d853895a.png">
  <img width="200" src="https://user-images.githubusercontent.com/39545165/58540562-928af000-8234-11e9-99bd-9fdf03d6453d.png">
</div>

## DB 구성

- 사용자(ID, 목적, 성별, 생년월일, 몸무게)
- 음식정보(이름, 중량, 칼로리, 탄수화물, 단백질, 지방)
- 아침, 점심, 저녁, 간식(번호, 이름, 중량, 칼로리, 탄수화물, 단백질, 지방)
- 날짜별 식단 기록(ID, 날짜, 아침, 점심, 저녁, 간식, 총 칼로리, 탄수화물, 단백질, 지방)

## 주요 기능

- 사용자의 목적(체중증가, 유지, 체중감소)에 따른 하루 권장 섭취량 제공
- 사용자의 일별 칼로리 섭취량 저장 및 달력을 통해 보여주기
- 음식 정보 가져오기, 사용자가 음식 정보 
