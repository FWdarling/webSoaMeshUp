package api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.StringReader;

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

@RestController
@RequestMapping("/api")
public class TranslateController {

    private XmlObject parseTXmlStr(String xml) throws JAXBException {
        StringReader reader = new StringReader(xml);
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlObject.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        XmlObject xmlObject = (XmlObject) jaxbUnmarshaller.unmarshal(reader);
        return xmlObject;
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
}
