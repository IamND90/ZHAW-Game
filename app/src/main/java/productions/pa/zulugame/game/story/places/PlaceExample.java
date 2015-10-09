package productions.pa.zulugame.game.story.places;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceExample extends AbstractStoryPlace {

    private static final String NAME = "";
    private static final String DESCRIPTION = "";
    private static final String STORY = "";

    public PlaceExample(int id){
        super(id,null);
    }

    @Override
    String getStory() {
        return STORY;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
