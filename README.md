# MusicCloud WebSOA

______

**Author:** Linjie Mu

**Student Number:** 1853204

**Tutor:** Liu Yan

**Technology stack**: springmvc+spring+springboot+java jdk1.8+maven

## 1. Introduction

----

This project is based on Netease Cloud music and combined with QQ music to enrich the music library as much as possible. Then, it provides even lyrics translation to facilitate users to see foreign language lyrics while listening to the music. Finally, we provide video association function for the name of the music currently played.



## 2.Usage

----

#### Access directly through the web page

Enter the following url to visit the api

http://fwdarling2020.cn:8080/ + api

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
2. Make sure your jdk version is jdk1.8 and your JAVA_HOME is set correctly
3. Make sure that ports 8080 for your running environment are available

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



## 4. Implemetation

----

### third-party api

| Origin            | BaseURL                                 |
| ----------------- | --------------------------------------- |
| QQMusic           | Depending on the deployment environment |
| NeteaseCloudMusic | Depending on the deployment environment |
| Youdao translate  | http://fanyi.youdao.com/openapi.do      |
| bilibili          | http://bilibili-service.daoapp.io       |

### use xml

#### src.main.java.api.TranslateController.java

```java
@XmlRootElement(name = "translation")
@XmlType(propOrder = {
        "paragraph",
})
class Translation{
    public String paragraph;
}

@XmlRootElement(name = "youdao-fanyi")
@XmlType(propOrder = {
        "errorCode",
        "query",
        "translation",
})
class XmlObject{
    public int errorCode;
    public String query;
    public Translation translation;
}


@RequestMapping(value = "/translate", method = RequestMethod.GET, params = "text")
    @ResponseBody
    public String translate(@RequestParam String text) throws JAXBException {
        if(text.length() > 200) return "The text is too long";
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fanyi.youdao.com/openapi.do?keyfrom=neverland&key=969918857&type=data&doctype=xml&version=1.1&q="+text + "&only=translate";
        String xml = restTemplate.getForObject(url, String.class);
        XmlObject xmlObject = parseTXmlStr(xml);
        return xmlObject.translation.paragraph;
    }
```

### return xml

#### src.main.java.api.SongXml.java

```java
@XmlRootElement(name = "song")
@XmlType(propOrder = {
        "type",
        "artist",
        "id",
        "name",
})
class Song{
    public int type;
    public String artist;
    public String id;
    public String name;

    public Song(){}

    public Song(int _type, String _artist, String _id, String _name){
        type = _type;
        artist = _artist;
        id = _id;
        name = _name;
    }

    public void setSong(int _type, String _artist, String _id, String _name){
        type = _type;
        artist = _artist;
        id = _id;
        name = _name;
    }
}

@XmlRootElement(name = "response")
public class SongXml {
    @XmlElement(name = "songs")
    public ArrayList<Song> songs;

    public SongXml(int n){
        songs = new ArrayList<Song>(n);
    }
     public SongXml(){};
}

```

src.main.java.api.SearchMusicController.java

```java
@RequestMapping(value = "/search/music", method = RequestMethod.GET, params = "key"
            , produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    public SongXml searchMusic(@RequestParam(value = "key")String key){
        ...
    }
```

## 5. Display