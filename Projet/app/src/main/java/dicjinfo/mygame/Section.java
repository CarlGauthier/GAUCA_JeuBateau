package dicjinfo.mygame;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Element
public class Section {

    @ElementListUnion({
        @ElementList(entry="rock", inline=true, type=Rock.class),
        @ElementList(entry="destroyableRock", inline=true, type=DestroyableRock.class),
        @ElementList(entry="heart", inline=true, type=Heart.class),
        @ElementList(entry="ammunition", inline=true, type=Ammunition.class),
        @ElementList(entry="coin", inline=true, type=Coin.class),
        @ElementList(entry="octo", inline=true, type=Octo.class)
    })

    private  ArrayList<GameObject> content;

    public ArrayList<GameObject> getContent() {
        return (ArrayList<GameObject>) content.clone();
    }

    Section() {
        content = new ArrayList<GameObject>();
    }
}