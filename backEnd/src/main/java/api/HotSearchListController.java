package api;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@RestController
@RequestMapping("/api")
public class HotSearchListController {

    private ArrayList<String> hotSearchNetease(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8030/search/hot";
        HashMap<String, HashMap<String, ArrayList<HashMap>>> res;
        res = restTemplate.getForObject(url,  HashMap.class);
        assert res != null;
        ArrayList<HashMap> arrayList =  res.get("result").get("hots");
        ArrayList<String> hotList = new ArrayList<String>(10);
        arrayList.forEach(l->{
            hotList.add(l.get("first").toString());
        });
        return hotList;
    }

    private ArrayList<String> hotSearchQQ(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://fwdarling2020.cn:8032/getHotkey";
        HashMap<String, HashMap<String, HashMap<String, ArrayList<HashMap>>>> res;
        res = restTemplate.getForObject(url,  HashMap.class);
        assert res != null;
        ArrayList<HashMap> arrayList =  res.get("response").get("data").get("hotkey");
        ArrayList<String> hotList = new ArrayList<String>(20);
        arrayList.forEach(l->{
            hotList.add(l.get("k").toString());
        });
        return hotList;
    }

    @RequestMapping(value = "/search/hotlist", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> hotSearchList(){
        ArrayList<String> neteaseList = hotSearchNetease();
        ArrayList<String> qqList = hotSearchQQ();
        neteaseList.addAll(qqList);
        return neteaseList;
    }
}
