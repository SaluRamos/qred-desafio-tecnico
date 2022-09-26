package scripts;
import java.util.TreeSet;

public class Smoothie{

    private String name;
    private TreeSet<String> ingredients;

    public String getName(){
        return this.name;
    }

    public TreeSet<String> getIngredients(){
        return this.ingredients;
    }

    public void setName(String name){
        this.name = name.toLowerCase();
    }

    public void setIngredients(String[] ingredients){
        TreeSet<String> ingredientsTree = new TreeSet<String>();
        for(int i = 0; i < ingredients.length; i++){
            ingredientsTree.add(ingredients[i].toLowerCase());
        }
        this.ingredients = ingredientsTree;
    }

}
