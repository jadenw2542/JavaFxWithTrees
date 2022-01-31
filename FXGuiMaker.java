/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class FXGuiMaker {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        FXComponentTree tree = new FXComponentTree();
        Boolean quit = true;
        while(quit) {
            printMenu();
            System.out.println("Please select an option:");
            String choice = in.nextLine().toUpperCase();
            Boolean validTree = false;
            switch (choice) {
                case ("L"):
                    System.out.println("Please enter filename:");
                    String fileName = in.nextLine();
                    try {
                        tree = FXComponentTree.readFromFile(fileName);
                        // C:\Users\jadwong\IdeaProjects\hw#5\src\hw5sample.txt
                        validTree = true;
                    } catch (FileNotFoundException e) {
                        validTree = false;
                        System.out.println("File not found");
                    } catch (Exception d) {
                        validTree = false;
                        d.printStackTrace();
                        System.out.println("Invalid Tree");
                    }
                    if(!(validTree)){
                        tree.setRoot(new FXTreeNode(null, ComponentType.AnchorPane, false, true, null));
                        tree.setCursor(tree.getRoot());
                        System.out.println("Empty tree with AnchorPane is created");
                    }

                    break;
                case ("P"):
                    System.out.println(PrintString(tree.getRoot(),tree,""));

                    break;
                case ("C"):
                    System.out.println("Please enter number of child (starting with 1):");
                    int childNum = Integer.parseInt(in.nextLine());
                    try {
                        tree.cursorToChild(childNum - 1);
                        System.out.println("Cursor Moved to " + tree.getCursor().toString());
                    }
                    catch (Exception f) {
                        System.out.println("Invalid Child");

                }

                    break;
                case ("R"):
                    try{
                        tree.cursorToRoot();
                        System.out.println("Cursor moved to " + tree.getCursor().toString());
                    } catch (Exception e){
                        System.out.println("Tree does not exist");
                    }

                    break;
                case ("A"):
                    System.out.println("Select component type (H - HBox, V - VBox, T - TextArea, B - Button, L - Label):");
                    ComponentType type = null;
                    FXTreeNode node = null;
                    String typeChoice = in.nextLine().toUpperCase();
                    System.out.println("Please enter text:");
                    String text = in.nextLine();
                    System.out.println("Please enter an index:");
                    int index = Integer.parseInt(in.nextLine()) - 1;
                    String typeString = "";
                    switch(typeChoice){
                        case("H"):
                            typeString = "HBox";
                            break;
                        case("V"):
                            typeString = "VBox";
                            break;
                        case("T"):
                            typeString = "TextArea";
                            break;
                        case("B"):
                            typeString = "Button";
                            break;
                        case("L"):
                            typeString = "Label";
                            break;
                    }

                    try{
                        type = FXComponentTree.typeOfComponent(typeString);
                    } catch(Exception e){
                        System.out.println("Not a valid Component");
                    }

                    if(type.equals(ComponentType.HBox) || type.equals(ComponentType.VBox)) {
                        node = new FXTreeNode(text, type, false, true, tree.getCursor());
                        try {
                            tree.addChild(index, node);
                        } catch (Exception e) {
                            System.out.println("index will make a hole");
                            e.printStackTrace();
                        }
                    }else{
                        node = new FXTreeNode(text, type, true, false, tree.getCursor());
                        try {
                            tree.addChild(index, node);
                        } catch (Exception e) {
                            System.out.println("index will make a hole");
                        }
                    }

                    break;
                case ("U"):
                    try{
                        tree.cursorToParent();
                        System.out.println("Cursor moved to " + tree.getCursor().toString());
                    } catch (Exception e){
                        System.out.println("Parent does not exist");
                    }

                    break;
                case ("E"):
                    System.out.println("Please enter new text:");
                    String newText = in.nextLine();
                    try {
                        tree.setTextAtCursor(newText);
                    }catch (Exception e){
                        System.out.println("node is a container and has no text");
                    }

                    break;
                case ("D"): // need to shift array
                    System.out.println("Please enter number of child (starting with 1):");
                    int indexDel = Integer.parseInt(in.nextLine()) - 1;
                    try {
                        tree.deleteChild(indexDel);
                    } catch (Exception e){
                        System.out.println("Invalid child");
                    }

                    break;
                case ("S"):
                    System.out.println("Please enter a filename:");
                    String fileOut = in.nextLine();

                    File file = new File(fileOut);
                    PrintWriter pw = new PrintWriter(System.out);
                    try {
                        pw = new PrintWriter(file);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    pw.println(tree.toFile(tree.getRoot(), tree, ""));
                    pw.close();

                    break;
                case ("X"):

                    break;
                case ("Q"):
                    System.out.println("Make like a tree and leave!");
                    quit = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

    }
    /*
    public static String escapePath(String path)
    {
        return path.replace("\\", "\\\\");
    }
    */

    /**
     * Prints tree recursively
     * @param node root of tree
     * @param tree current tree
     * @param space used to format tree of each node's parents and child and which nodes is the cursor
     * @return tree converted into a String
     */
    public static String PrintString(FXTreeNode node, FXComponentTree tree, String space) { //root
       String out ="";
       if(node == null){
           return "";
       }
       if(node == tree.getRoot() && node == tree.getCursor()){
           out = out + space + "==>" + node.toString() + "\n";
       }
       else if(node == tree.getRoot()){
           out = out + space + node.toString() + "\n";
       }
       else if(node == tree.getCursor()) {
           out = out + space + "==>" + node.toString() + "\n";
       }else{
           out = out + space + "+--" + node.toString() + "\n";
       }
       if(node.hasChildren()){ //loop Children
           for(int i =0; i < node.getChildren().length; i++){
               out += PrintString(node.getChildren()[i], tree, space + "   ");
           }
       }
       return out;
    }

    /**
     * prints the menu
     */
    public static void printMenu(){
        System.out.println("Menu:\n" +
                "    L) Load from file\n" +
                "    P) Print tree\n" +
                "    C) Move cursor to a child node\n" +
                "    R) Move cursor to root\n" +
                "    A) Add a child\n" +
                "    U) Cursor up (to parent)\n" +
                "    E) Edit text of cursor\n" +
                "    D) Delete child\n" +
                "    S) Save to file\n" +
                "    Q) Quit");

    }
}
