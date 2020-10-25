package api;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlRootElement(name = "song")
@XmlType(propOrder = {
        "type",
        "artist",
        "id",
        "name",
})
class Song{
    public int type;
    public String artist;
    public String id;
    public String name;

    public Song(){}
    
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

@XmlRootElement(name = "response")
public class SongXml {
    @XmlElement(name = "songs")
    public ArrayList<Song> songs;

    public SongXml(int n){
        songs = new ArrayList<Song>(n);
    }
     public SongXml(){};
}
