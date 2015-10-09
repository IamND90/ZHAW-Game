package productions.pa.zulugame.game.story.places;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.models.AbstractModel;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.story.StoryTree;

/**
 * Created by Andrey on 08.10.2015.
 */
public abstract class AbstractStoryPlace extends AbstractModel {



    final List<IModel> availableModels = new ArrayList<>();

    final List<String> availableCommands = new ArrayList<>();

    String commandBundle;

    private final List<AbstractStoryPlace> nextPlaces = new ArrayList<>();
    private final AbstractStoryPlace parentPlace;


    public AbstractStoryPlace(int id, AbstractModel.TYPE type,AbstractStoryPlace parent){
        super(id,type);
        parentPlace = parent;
    }


    public void addBranch(AbstractStoryPlace place){
        nextPlaces.add(place);
    }

    AbstractStoryPlace getPreviousPlace(){return parentPlace;}
    AbstractStoryPlace getPlaceAtIndex(int index){
        //if(index > 0 && index < nextPlaces.size())return nextPlaces.get(index);
        return StoryTree.get().getPlaceAtId(getId(), index);
    }


    public abstract String getStory();

}
