## MusicCloud

____

**Author:** Mu Linjie

**Student Number:** 1853204

**Tutor:** Liu Yan

## 1. Introduction

 This project is based on Netease Cloud music and combined with QQ music to enrich the music library as much as possible. Then, it provides even lyrics translation to facilitate users to see foreign language lyrics while listening to the music. Finally, we provide video association function for the name of the music currently played.

## 2.Web-api Used

| Origin            | BaseURL                                                  |
| ----------------- | -------------------------------------------------------- |
| QQMusic           | Depending on the deployment environment                  |
| NeteaseCloudMusic | Depending on the deployment environment                  |
| Youdao translate  | http://fanyi.youdao.com/openapi.do                        |
| bilibili          | http://bilibili-service.daoapp.io                        |

## 3.Module Relationship

- Users search for music to play or play music randomly, system will find songs from NetEase Cloud or QQ music interface,

- For lyrics can be clicked to translate, call Google translation interface for real-time translation.
- For the song currently playing, you can also click recommend and call bilibili interface to find relevant videos.

