package api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class PlayMusicApi {

    private String playMusicNetease(String id){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8030/song/url?id=" + id;
        HashMap<String, ArrayList<HashMap<String, String>>> res = restTemplate.getForObject(url, HashMap.class);
        return Objects.requireNonNull(res).get("data").get(0).get("url");
    }

    private String playMusicQQ(String id){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8032/getMusicVKey?songmid=" + id;
        HashMap<String, HashMap> res = restTemplate.getForObject(url, HashMap.class);
        return ((ArrayList) Objects.requireNonNull(res).get("response").get("playLists")).get(0).toString();
    }

    @RequestMapping(value = "/play/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String playMusic(@PathVariable String id, @RequestParam(value = "type", defaultValue = "0")int type){
        if(type == 0) return playMusicNetease(id);
        else if(type == 1) return playMusicQQ(id);
        else return null;
    }
}
