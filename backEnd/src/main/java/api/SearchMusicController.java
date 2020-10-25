package api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


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

    @RequestMapping(value = "/search/music", method = RequestMethod.GET, params = "key"
            , produces = {"application/xml;charset=UTF-8"})
    @ResponseBody
    public SongXml searchMusic(@RequestParam(value = "key")String key){
        SongXml songXml = new SongXml(10);
        ArrayList<Song> res = songXml.songs;
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
        return songXml;
    }
}
