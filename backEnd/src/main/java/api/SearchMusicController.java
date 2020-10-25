package api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

class Song{
    public int type;
    public String artist;
    public String id;
    public String name;

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

@RestController
@RequestMapping("/api")
public class SearchMusicController {

    private ArrayList searchNeteaseMusic(String key){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8030/search?keywords=" +
                key + "&limit=5&type=1";
        HashMap<String, HashMap<String, ArrayList>> res;
        res = restTemplate.getForObject(url, HashMap.class);
        assert res != null;
        return res.get("result").get("songs");
    }

    private ArrayList searchQQMusic(String key){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8032/getSearchByKey?key=" +
                key + "&limit=5";
        HashMap<String, HashMap<String, HashMap<String, HashMap<String, ArrayList>>>> res;
        res = restTemplate.getForObject(url, HashMap.class);
        assert res != null;
        return res.get("response").get("data").get("song").get("list");
    }

    @RequestMapping(value = "/search/music", method = RequestMethod.GET, params = "key")
    @ResponseBody
    public ArrayList<Song> searchMusic(@RequestParam(value = "key")String key){
        ArrayList<Song> res = new ArrayList<Song>(10);
        ArrayList neteaseSongs = searchNeteaseMusic(key);
        for(int i = 0; i < 5; i++){
            LinkedHashMap temMap = (LinkedHashMap) neteaseSongs.get(i);
            String id = temMap.get("id").toString();
            String name = temMap.get("name").toString();
            ArrayList<LinkedHashMap> artists = (ArrayList<LinkedHashMap>) temMap.get("artists");
            String artist = artists.get(0).get("name").toString();
            res.add(new Song(0, artist, id, name));
        }
        ArrayList qqSongs = searchQQMusic(key);
        for(int i = 0; i < 5; i++){
            LinkedHashMap temMap = (LinkedHashMap) qqSongs.get(i);
            String mid = temMap.get("mid").toString();
            String name = temMap.get("title").toString();
            ArrayList<LinkedHashMap> artists = (ArrayList<LinkedHashMap>) temMap.get("singer");
            String artist = artists.get(0).get("name").toString();
            res.add(new Song(1, artist, mid, name));
        }
        return res;
    }
}
