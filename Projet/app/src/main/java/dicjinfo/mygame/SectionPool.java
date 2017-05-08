package dicjinfo.mygame;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class SectionPool {

    @ElementList
    private ArrayList<Section> sections;

    public ArrayList<Section> getSections() {
        return sections;
    }

    SectionPool() {
        sections = new ArrayList<Section>();
    }
}