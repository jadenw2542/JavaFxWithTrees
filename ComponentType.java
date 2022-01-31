/**
 * Jaden Wong
 * SBU ID: 113469617
 * jaden.wong@stonybrook.edu
 * CSE 214.R02 Data Structures - Fall 2021
 */
public enum ComponentType {
    Button("Button"), Label("Label"), TextArea("TextArea"), HBox("HBox"), VBox("VBox"), AnchorPane("AnchorPane");
    private final String type;
    ComponentType(String type){this.type = type;}
    public String getComponent(){ return type;}
}
