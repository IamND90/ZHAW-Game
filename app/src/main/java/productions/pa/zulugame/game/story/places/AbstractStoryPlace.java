package productions.pa.zulugame.game.story.places;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.story.IModel;
import productions.pa.zulugame.game.story.StoryTree;

/**
 * Created by Andrey on 08.10.2015.
 */
public abstract class AbstractStoryPlace implements IModel {

    private int id;

    final List<IModel> availableModels = new ArrayList<>();

    final List<String> availableCommands = new ArrayList<>();

    String commandBundle;

    private final List<AbstractStoryPlace> nextPlaces = new ArrayList<>();
    private final AbstractStoryPlace parentPlace;


    public AbstractStoryPlace(int id,AbstractStoryPlace parent){
        this.id = id;
        parentPlace = parent;
    }

    public int getId(){return id;}


    AbstractStoryPlace getPreviousPlace(){return parentPlace;}
    AbstractStoryPlace getNextPlace(int index){
        //if(index > 0 && index < nextPlaces.size())return nextPlaces.get(index);
        return StoryTree.getPlaceAtId(id, index);
    }


    abstract String getStory();
}
