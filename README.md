# klg-task

## Uruchamianie
Wymagane: Java 11

Poniższa komenda zbuduje projekt oraz go uruchomi(uruchomiona w katalogu z projektem):
```
./mvnw clean package spring-boot:run
```

## Features
Testowane za pomocą **Postman**

##### 1.Dodawanie nowej rezerwacji
[dodać nową rezerwację (nie może być dwóch rezerwacji tego samego obiektu w tym samym czasie)]
```
POST: */api/v1/reservation
```
wymagane wprowadzenie danych. Przykładowy JSON:
```
{
    "startRentDate": "2033-07-05",
    "endRentDate": "2033-10-08",
    "cost": 9999,
    "tenant": 1,
    "landlord": 4,
    "toRent": 1
}
```
- startRentDate = data poczatku rezerwacji. Wymagana, musi być w przyszłości.
- endRentDate = data końca rezerwacji. Wymagana, musi być w przyszłości.
- cost = koszt rezerwacji. Wymagane.
- tenant = id osoby najmującej. Wymagane.
- landlord = id osoby wynajmującej. Wymagane.
- toRent = id obiektu do wynajęcia. Wymagane.

Przykad:
```
POST: */api/v1/reservation
BODY:
{
    "startRentDate": "2033-07-05",
    "endRentDate": "2033-10-08",
    "cost": 9999,
    "tenant": 1,
    "landlord": 4,
    "toRent": 1
}
```

##### 2.Modyfikowanie istniejącej rezerwacji
(zmienić rezerwację)
```
PUT: */api/v1/reservation
```
wymagane wprowadzenie danych. Przykładowy JSON:
```
{
    "reservationId" : 1
    "startRentDate": "2033-07-05",
    "endRentDate": "2033-10-08",
    "cost": 9999,
    "tenant": 1,
    "landlord": 4,
    "toRent": 1
}
```
- reservationId = id rezerwacji do modyfikacji. Wymagane.
- startRentDate = data poczatku rezerwacji. Wymagana, musi być w przyszłości.
- endRentDate = data końca rezerwacji. Wymagana, musi być w przyszłości.
- cost = koszt rezerwacji. Wymagane.
- tenant = id osoby najmującej. Wymagane.
- landlord = id osoby wynajmującej. Wymagane.
- toRent = id obiektu do wynajęcia. Wymagane.

Przykad:
```
PUT: */api/v1/reservation
BODY:
{
    "reservationId" : 1
    "startRentDate": "2033-07-05",
    "endRentDate": "2033-10-08",
    "cost": 9999,
    "tenant": 1,
    "landlord": 4,
    "toRent": 1
}
```

##### 3.Pobieranie listy rezerwacji dla zadanego najemcy
[pobrać listę rezerwacji dla zadanego najemcę (nazwa)]

```
GET: */api/v1/reservation/tenant/{name}
```
- name = nazwa najemcy

Przykad:
```
GET: */api/v1/reservation/tenant/JohnDoe
```

##### 4.Pobieranie listy rezerwacji dla danego obiektu
(pobrać listę rezerwacji dla danego obiektu)
```
GET: */api/v1/reservation/torent/{name}
```
- name = nazwa obiektu

Przykad:
```
GET: */api/v1/reservation/torent/LaLand
```

