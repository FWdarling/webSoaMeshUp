package api;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

class Video{
    public String url;
    public String title;
    public String author;
    public String description;

    public Video(String _url, String _title, String _author, String _desc){
        url = _url;
        title = _title;
        author = _author;
        description = _desc;
    }
}

@RestController
@RequestMapping("/api")
public class SearchVideoController {

    @RequestMapping(value = "/video", method = RequestMethod.GET, params = "keyword")
    @ResponseBody
    public ArrayList<Video> searchVideo(@RequestParam String keyword,
                              @RequestParam(value = "order", defaultValue = "totalrank") String order,
                              @RequestParam(value = "page", defaultValue = "1")int page){

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.bilibili.com/x/web-interface/search/type?search_type=video&keyword=" +
                keyword + "&order=" + order + "&page=" + page;
        HashMap<String, HashMap<String, ArrayList<HashMap>>> res;
        res = restTemplate.getForObject(url, HashMap.class);
        ArrayList<HashMap> arrayList = res.get("data").get("result");
        ArrayList<Video> videos = new ArrayList<Video>(30);
        arrayList.forEach(e->{
            String arcurl = e.get("arcurl").toString();
            String title = e.get("title").toString();
            String author = e.get("author").toString();
            String desc = e.get("description").toString();
            videos.add(new Video(arcurl, title, author, desc));
        });
        return videos;
    }

}
