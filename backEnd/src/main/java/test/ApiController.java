package test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequestMapping(value = "/search/music", method = RequestMethod.GET, params = "key")
    @ResponseBody
    public String searchMusic(@RequestParam(value = "key")String key){
        return "key = " + key;
    }

    @RequestMapping(value = "/search/tips", method = RequestMethod.GET, params = "key")
    @ResponseBody
    public String searchTips(@RequestParam String key){
        return "key = " + key;
    }

    @RequestMapping(value = "/search/hotlist", method = RequestMethod.GET)
    @ResponseBody
    public String hotSearchList(){
        return "hotLists";
    }

    @RequestMapping(value = "/lyrics/{songId}", method = RequestMethod.GET)
    @ResponseBody
    public String getLyrics(@PathVariable String songId){
        return "songId = " + songId;
    }

    @RequestMapping(value = "/translate", method = RequestMethod.GET, params = "text")
    @ResponseBody
    public String translate(@RequestParam String text){
        return "text = " + text;
    }

    @RequestMapping(value = "/video", method = RequestMethod.GET, params = "keyword")
    @ResponseBody
    public String searchVideo(@RequestParam String keyword,
                              @RequestParam(value = "order", defaultValue = "totalrank") String order,
                              @RequestParam(value = "page", defaultValue = "1")int page){
        return "keyword = " + keyword + "+ order = " + order + "+ page = " + page;
    }

    @RequestMapping(value = "/play/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String palyMusic(@PathVariable String id){
        return "id = " + id;
    }
}
