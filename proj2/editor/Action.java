package editor;

/**
 * Created by Jesse on 3/7/2016.
 */
public class Action {
    private String typeOfAction;

    private Node position;
    private Node content;

    public Action(String typeOfAction, Node position, Node content) {
        this.typeOfAction = typeOfAction;
        this.position = position;
        this.content = content;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    public Node getActionPosition() {
        return position;
    }

    public Node getContent() {
        return content;
    }
}
