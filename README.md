# Creature Creator (Backend)

This program allows the user customize their own creatures. The user is able to store and read data about creatures via an API. Each creature has the following characteristics:

- Name (String)
- Body Shape (Square, circle, bumpy, spiky, star, heart, pentagon, hexagon, or octagon)
- Body Color (Hexadecimal)
- Eye Shape (Square, circle, bumpy, spiky, star, heart, pentagon, hexagon, or octagon)
- Eye Color (Hexadecimal)
- Antenna (Boolean)
- Horns (Boolean)
- Tail (Boolean)
- Ears (Boolean)
- Proboscis (Boolean)

## Prerequisites

In order to run the program, make sure the following are installed on your computer:

- Java (version 17+)

## Execution

To run the program, execute the following command:

```
./mvnw spring-boot:run
```

Alternatively, to run tests to make sure the program functions properly, run:

```
./mvnw test
```

## Usage

Once the program is running, you can make API requests to the database. Here are examples of valid API requests:

```
http POST localhost:8080/creature-creator/api name=Sarah bodyShape=SQUARE bodyColor=#00FF00 eyeShape=STAR eyeColor=#FF00FF antenna:=true horns:=false tail:=false ears:=false proboscis:=true
http PUT localhost:8080/creature-creator/api/1 name=Sarah bodyShape=CIRCLE bodyColor=#00FF00 eyeShape=STAR eyeColor=#FF00FF antenna:=true horns:=false tail:=false ears:=false proboscis:=true
http GET localhost:8080/creature-creator/api
http GET localhost:8080/creature-creator/api/1
http DELETE localhost:8080/creature-creator/api/1
```