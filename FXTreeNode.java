/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
public class FXTreeNode {
    private String text;
    private ComponentType type;
    private FXTreeNode parent;
    private FXTreeNode[] children;
    final int maxChildren = 10;

    /**
     * Creates FXTreeNode object
     * @param text text of object
     * @param type type of componenet
     * @param isControl if control, it has no children else empty array of children
     * @param isContainer if container, it has no text, else it has text
     * @param parent parent node of current node
     */
    public FXTreeNode(String text, ComponentType type, boolean isControl, boolean isContainer, FXTreeNode parent){
        this.type = type;
        this.parent = parent;
        if(isControl){
            this.children = null;
        }
        else{
            this.children = new FXTreeNode[maxChildren];
        }
        if(isContainer){
            this.text = null;
        }
        else{
            this.text = text;
        }
    }



    /**
     * sets the text of current node
     * @param text text to be set to node
     * @throws Exception if node is is a conatainer, it has no text
     */
    public void setText(String text) throws Exception {
        if(this.text == null){
            throw new Exception();
        }
        this.text = text;
    }
    public ComponentType getType() {
        return type;
    }
    public String getText() {
        return text;
    }
    public void setType(ComponentType type) {
        this.type = type;
    }

    /**
     * returns parent of current node
     * @return node that is parent of current node
     * @throws Exception if parent does not exist
     */
    public FXTreeNode getParent() throws Exception{
        if(parent == null){
            throw new Exception();
        }
        return parent;
    }
    public void setParent(FXTreeNode parent) {
        this.parent = parent;
    }
    public FXTreeNode[] getChildren() {
        return children;
    }

    public int getMaxChildren() {
        return maxChildren;
    }

    /**
     * changes children of node
     * @param children node[] to be changed to
     * @throws Exception if children is null
     */
    public void setChildren(FXTreeNode[] children)throws Exception {
        if(!(hasChildren())){
            throw new Exception();
        }
        this.children = children;
    }

    /**
     * checks if node has children
     * @return true if node has childre and false if it doesn't
     */
    public Boolean hasChildren(){
        if(children == null){
            return false;
        }
        return true;
    }

    /**
     * Prints node in a readable format
     * @return String to be printed
     */
    public String toString(){
        String temp = type.toString();
        if(text != null){
            temp += ": " + text;
        }
        return temp;

    }
    public String toStringFile(){
        String temp = type.toString();
        if(text != null){
            temp += " " + text;
        }
        return temp;

    }
}
