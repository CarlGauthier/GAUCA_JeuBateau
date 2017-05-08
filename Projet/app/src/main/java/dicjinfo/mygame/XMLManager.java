package dicjinfo.mygame;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Node;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class XMLManager {

    public XMLManager() {

    }

    public Section getSection(int number) {

        Serializer serializer = new Persister();
        SectionPool sectionPool = null;
        try {
            InputStream stream = GameActivity.getAppContext().getAssets().open("sections.xml");
            sectionPool = serializer.read(SectionPool.class, stream);
        } catch (Exception e) {

        }
        Section section = sectionPool.getSections().get(number);
        return section;
    }
}