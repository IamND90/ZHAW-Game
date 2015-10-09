package productions.pa.zulugame.game.models.places;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.models.AbstractModel;

/**
 * Created by Andrey on 08.10.2015.
 */
public abstract class AbstractStoryPlace extends AbstractModel {




    private final List<AbstractStoryPlace> nextPlaces = new ArrayList<>();
    private AbstractStoryPlace parentPlace = null;


    public AbstractStoryPlace(int id){
        super(id,TYPE.PLACE);
    }

    public List<AbstractStoryPlace> getNextPlaces() {
        return nextPlaces;
    }

    public void addBranch(AbstractStoryPlace place){
        nextPlaces.add(place);
        nextPlaces.get(nextPlaces.size()-1).setParentPlace(this);
    }

    AbstractStoryPlace getPreviousPlace(){return parentPlace;}

    public AbstractStoryPlace getParentPlace() {
        return parentPlace;
    }

    public void setParentPlace(AbstractStoryPlace parentPlace) {
        this.parentPlace = parentPlace;
    }

    public abstract String getStory();

}
