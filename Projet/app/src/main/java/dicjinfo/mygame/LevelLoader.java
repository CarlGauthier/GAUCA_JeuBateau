package dicjinfo.mygame;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LevelLoader {

    /*XmlPullParser parser;

    public LevelLoader(Context context, ArrayList<GameObject> gameObjectArray, ArrayList<CollidableGameObject> collidableArray, ArrayList<DynamicGameObject> dynamicArray) {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            parser = pullParserFactory.newPullParser();

            InputStream inputStream = context.getAssets().open();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

        } catch (XmlPullParserException e) {
        }
    }*/
}