/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
public class FXComponentTree {
    private FXTreeNode root;
    private FXTreeNode cursor;
    public FXComponentTree(){
    }

    /**
     * Moves cursor to root of tree
     * @throws Exception if root is null or tree doesn't exist
     */
    public void cursorToRoot() throws Exception{ //Sets the cursor to the root of the FXComponentTree
        if(root == null){
            throw new Exception();
        }
        cursor = root;
    }

    /**
     * Deletes the node's child at the specified index
     * @param index index of the node to be deleted
     * @throws Exception if cursor doesnt have children or there is no node at specified index
     */
    public void deleteChild(int index)throws Exception{ //Removes the child at the specified index of the FXComponentTree, as well as all of its children.
        if(!(cursor.hasChildren())){
            throw new Exception();
        }
        if(cursor.getChildren()[index] == null){
            throw new Exception();
        }
        for(int i = index; i < cursor.getMaxChildren() - 1; i++){
            cursor.getChildren()[i] = cursor.getChildren()[i+1];
        }
        cursor.getChildren()[cursor.getMaxChildren()-1] = null;

    }

    /**
     * Adds node to the current node's childlren[] at sepcified index
     * @param index index to be added to
     * @param node node to be added
     * @throws Exception if node is a Container or children[] is full or if it will make a hole
     */
    public void addChild(int index, FXTreeNode node) throws Exception{// Adds the given node to the corresponding index of the children array.
        //Should throw an exception if adding the node at the specified index makes a hole in the array.
        if(cursor.getChildren() == null){
            throw new Exception();
        }
        if(cursor.getChildren()[cursor.getChildren().length - 1] != null){
            throw new Exception();
        }
        if(index != 0 && index != cursor.getMaxChildren()){
            if(cursor.getChildren()[index + 1] == null && cursor.getChildren()[index - 1] == null){
                throw new Exception();
            }
        }
        int last = -1;
        for(int i =0; i < cursor.getChildren().length; i++){
            if(cursor.getChildren()[i] == null){
                last = i;
                break;
            }
        }
        for(int i = last; i > index; i--){
            cursor.getChildren()[i] = cursor.getChildren()[i-1];
        }
        cursor.getChildren()[index] = node;
    }

    /**
     * ets the current node’s text to the specified text.
     * @param text what the text should be set io
     * @throws Exception if node is a container
     */

    public void setTextAtCursor(String text)throws Exception{//Sets the current node’s text to the specified text.
        cursor.setText(text);

    }

    /**
     * sets cursor the specified index of the node's children[]
     * @param index index to be set to
     * @throws Exception if the node's children[index] does not exist
     */
    public void cursorToChild(int index) throws Exception{ //Moves the cursor to the child node of the of the cursor corresponding to the specified index.
        if(cursor.getChildren()[index] == null){
            throw new Exception();
        }
        cursor = cursor.getChildren()[index];
    }

    /**
     * Moves the cursor to the parent of the current node.
     * @throws Exception if node has no parent
     */
    public void cursorToParent() throws Exception{ // Moves the cursor to the parent of the current node.
        cursor = cursor.getParent();
    }

    /**
     * Generates a text file that reflects the structure of the FXComponentTree.
     * @param filename file to be read
     * @return a tree
     * @throws FileNotFoundException if file is not found
     * @throws Exception if component type is invalid
     */
    public static FXComponentTree readFromFile(String filename) throws FileNotFoundException, Exception{ //    Generates the FXComponentTree based on the file name that is passed in.
        System.out.println(filename);
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        FXComponentTree tree = new FXComponentTree();
        FXTreeNode treeNode = null;
        while(sc.hasNextLine()){
            FXTreeNode node;
            String line = sc.nextLine();
            if(line.equals("")){
                break;
            }
            String depthPos = line.substring(0,line.indexOf(" ")); // gets string of numbers (0-0-1)
            line = line.substring(line.indexOf(" ") + 1); //Button: text or VBox

            if(treeNode == null){
                treeNode = new FXTreeNode(null, typeOfComponent(line), false, true,null);
            }
            else {
                    FXTreeNode nodeTemp = tree.addNode(depthPos, treeNode);
                    if (line.contains(" ")) {
                        String componentName = line.substring(0, line.indexOf(" ")); //**
                        String text = line.substring(line.indexOf(" ") + 1); //**
                        node = new FXTreeNode(text, typeOfComponent(componentName), true, false, nodeTemp);

                    } else {
                        node = new FXTreeNode(null, typeOfComponent(line), false, true, nodeTemp);
                    }
                    nodeTemp.getChildren()[Integer.parseInt(depthPos.substring(depthPos.length() - 1))] = node;
            }
        }
        tree.setRoot(treeNode);
        tree.setCursor(treeNode);
        System.out.println();

        sc.close();
        return tree;
    }

    /**
     * recursive function to find the parent of each node in each line
     * @param nodeString each line of thE text file converted to a iterable string
     * @param root root of node
     * @return parent of each node in each line
     * @throws Exception if tree does not exist
     */
    public FXTreeNode addNode(String nodeString, FXTreeNode root) throws Exception {
        if(root == null){
            throw new Exception();
        }
        if (nodeString.length() == 1) {
            return root;
        }
        if (nodeString.length() == 3) {
            return root;
        }
        else{
           return addNode(nodeString.substring(2), root.getChildren()[Integer.parseInt(nodeString.substring(2,3))]);
        }

    }

    /**
     * recursivle loops tree to return a string which is then written to a file
     * @param node root of tree
     * @param tree tree that is read
     * @param space formatting String
     * @return String to be written to a file
     */
    public String toFile(FXTreeNode node, FXComponentTree tree, String space){
        String out ="0";
        if(node == null){
            return "";
        }
        else{
            out = out + space + " " + node.toStringFile() + "\n";
        }
        if(node.hasChildren()){ //loop Children
            for(int i =0; i < node.getChildren().length; i++){
                out += toFile(node.getChildren()[i], tree, space + "-" + i );
            }
        }
        return out;
    }
    public static void exportToFXML(FXComponentTree tree, String filename) {

    }      // Extra Credit
   // Postconditions:
   // A valid FXML file that can be opened in SceneBuilder has been created.
   // The dimensions do not matter too much, we will check by looking at the tree view in the bottom right.

    public FXTreeNode getRoot() {
        return root;
    }
    public void setRoot(FXTreeNode root) {
        this.root = root;
    }

    public FXTreeNode getCursor() {
        return cursor;
    }

    public void setCursor(FXTreeNode cursor) {
        this.cursor = cursor;
    }

    /**
     * returns the Componenet Type enum from a String
     * @param temp string to be read
     * @return ComponentType object
     * @throws Exception if string is not a valid ComponentType Type
     */
    public static ComponentType typeOfComponent(String temp) throws Exception{
        switch(temp){
            case("AnchorPane"):
                return ComponentType.AnchorPane;
            case("Button"):
                return ComponentType.Button;
            case("Label"):
                return ComponentType.Label;
            case("TextArea"):
                return ComponentType.TextArea;
            case("HBox"):
                return ComponentType.HBox;
            case("VBox"):
                return ComponentType.VBox;
            default:
                throw new Exception();
        }
    }
}

