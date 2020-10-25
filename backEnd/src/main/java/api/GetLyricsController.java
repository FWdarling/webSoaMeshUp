package api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class GetLyricsController {

    private String getLyricsNetease(String songId){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8030/lyric?id=" + songId;
        HashMap<String, HashMap<String, String>> res;
        res = restTemplate.getForObject(url, HashMap.class);
        return Objects.requireNonNull(res).get("lrc").get("lyric");
    }

    private String getLyricsQQ(String songId){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8032/getLyric?songmid=" + songId;
        HashMap<String, HashMap<String, String>> res;
        res = restTemplate.getForObject(url, HashMap.class);
        return Objects.requireNonNull(res).get("response").get("lyric");
    }

    @RequestMapping(value = "/lyrics/{songId}", method = RequestMethod.GET)
    @ResponseBody
    public String getLyrics(@PathVariable String songId,
                            @RequestParam(defaultValue = "0")int type){
        if(type == 0) return getLyricsNetease(songId);
        else if(type == 1) return getLyricsQQ(songId);
        else return null;
    }
}
