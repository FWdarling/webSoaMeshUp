# MusicCloud WebSOA

______

**Author:** Linjie Mu

**Student Number:** 1853204

**Tutor:** Liu Yan

## 1. Introduction

----

This project is based on Netease Cloud music and combined with QQ music to enrich the music library as much as possible. Then, it provides even lyrics translation to facilitate users to see foreign language lyrics while listening to the music. Finally, we provide video association function for the name of the music currently played.



## 2.Usage

----

#### Access directly through the web page

Enter the following url to visit the homepage

http://fwdarling2020.cn:8080/

#### Or deploy the backend locally

##### Clone Source Code from my github

```
git clone https://github.com/FWdarling/webSoaMeshUp
```



##### Start the third-party interface

1. Install node.js and npm and make sure your version is the latest stable version
2. Make sure that ports 3000 and 3200 for your running environment are available

```
cd webSoaMeshUp
run third-party-api/import.sh
```

##### Use Maven to run this project

1. Install maven and make sure your version is the latest stable version
2. Make sure that ports 8080 for your running environment are available

```
cd backend
./mvnw spring-boot:run
```

##### Now you can open frontend homepage to use the web application



## 3. Function Overview

----

### Search music(return XML)

- Details: search for music by keyword
- Interface address: /api/search/music  GET
- Required parameters: key
- Optional parameters: none
- Example: /api/search/music?key=loseyourself

### Hot search list

- Details: get the hot search list
- Interface address: /api/search/hotlist GET
- Required parameters: none
- Optional parameters: none
- Example: /api/search/hotlist

### Get lyrics

- Details: get the lyrics to the selected music
- Interface address: /api/lyrics/{songId} GET
- Required parameters: songId, type
- Optional parameters: type( 0(default)--Netease, 1--QQ)
- Example:  /api/lyrics/123456?type=1

### Translate (parse XML)

- Details: translate lyrics and music name 
- Interface address: /api/translate GET
- Required parameters:text
- Optional parameters:
- Example: /api/translate?text=loseyourself

### Related video search

- Details: Search related videos in bilibili by song name

- Interface address: /api/video GET

- Required parameters: keyword

- Optional parameters:order(default: totalrank), page(default: 1)

    

    | order | Comprehensive Ranking | totalrank |
    | ----- | --------------------- | --------- |
    |       | Most clicks           | click     |
    |       | Latest release        | pubdate   |
    |       | Most barrage          | dm        |
    |       | Most Favorites        | stow      |
    |       | Most Commented        | scores    |

    

- Example: /api/video?keyword=loseyourself&order=totalrank&page=1

### Play music

- Details: Play the selected music
- Interface address: /api/play/{id} GET
- Required parameters: id
- Optional parameters: type( 0(default)--Netease, 1--QQ)
- Example: /api/play/33894312
