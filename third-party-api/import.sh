#!/bin/bash
#install Netease Cloud Api
rm -rf NeteaseCloud
mkdir NeteaseCloud
cd NeteaseCloud/
git clone git@github.com:Binaryify/NeteaseCloudMusicApi.git
cd NeteaseCloudMusicApi
npm install
cd ..
cd ..

#install Netease Cloud Api
rm -rf QQMusic
mkdir QQMusxric
cd QQMusic/
git clone git@github.com:Rain120/qq-music-api.git
cd qq-music-api
npm install
cd ..
cd ..