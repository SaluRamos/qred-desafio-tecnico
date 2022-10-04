import scripts.Smoothie;
import java.io.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Ingredients{

    public static Map<String, Smoothie> smoothies = new HashMap<String, Smoothie>();

    public static void main(String[] args) throws IOException{
        initSmoothies();
        //imprime informações dos smoothies na memoria do sistema
        System.out.println("\nFunctional Smooothies Inc Menu: ");
        String[] smoothieKeys = Ingredients.smoothies.keySet().toArray(new String[Ingredients.smoothies.size()]);
        for(String key : smoothieKeys){
            String value = Ingredients.smoothies.get(key).getIngredients().toString();
            System.out.println(key + " = " + value);
        }
        //inicia novo pedido
        Scanner inputScanner = new Scanner(System.in);
        while(true){
            System.out.printf("\n");
            System.out.printf("Type new request: ");
            String inputRequest = inputScanner.nextLine();
            String response = newRequest(inputRequest);
            System.out.println("api response = " + response);
            System.out.printf("Want to make a new request? ('n' to no, anything else to yes) ");
            String inputQuestionNewRequest = inputScanner.nextLine().toLowerCase();
            if(inputQuestionNewRequest.equals("n")){
                System.out.println("BYE BYE!");
                break;
            }
        }
        inputScanner.close();
    }

    //novo pedido
    private static String newRequest(String smoothieRequest) throws IllegalArgumentException{
        List<String> smoothieParts = Arrays.asList(smoothieRequest.toLowerCase().split(","));
        String smoothieName = smoothieParts.get(0);
        List<String> undesiredIngredients = new ArrayList<String>();
        //interage entre todos os elementos
        for(int i = 1; i < smoothieParts.size();i++){
            if(smoothieParts.get(i).charAt(0) == '-'){
                StringBuilder ingredientGap = new StringBuilder(smoothieParts.get(i));
                ingredientGap.deleteCharAt(0);
                undesiredIngredients.add(ingredientGap.toString());
            }else{
                throw new IllegalArgumentException("a operação deste ingrediente não existe");
            }
        }
        //verifica se o smoothie esta no banco de dados
        String[] smoothieKeys = Ingredients.smoothies.keySet().toArray(new String[Ingredients.smoothies.size()]);
        if(Arrays.asList(smoothieKeys).contains(smoothieName) == false){
            throw new IllegalArgumentException("o smoothie não está no banco de dados");
        }
        //puxa informações do smoothie desejado e realiza as operações sob ingredientes indesejados
        StringBuilder recipe = new StringBuilder();
        for(String value : Ingredients.smoothies.get(smoothieName).getIngredients()){ //a ordem de iteração já está ordenada
            if(undesiredIngredients.contains(value) == false){ //verifica se o ingrediente está na lista de ingredientes indesejados
                recipe.append(value + ","); //adiciona ao final da string o ingrediente
            }
        }
        if(recipe.length() > 1){
            recipe.deleteCharAt(recipe.length() - 1); //remove a virgula final da string
        }else{
            throw new IllegalArgumentException("o smoothie não possui ingredientes");
        }
        return recipe.toString();
    }

    //inicia os smoothies no banco de dados
    private static void initSmoothies() throws IOException{
        String smoothiesTextFilePath = System.getProperty("user.dir") + "\\smoothies.txt";
        File fSmoothiesPath = new File(smoothiesTextFilePath);
        FileInputStream fSmoothiesStream = new FileInputStream(fSmoothiesPath);
        InputStreamReader fSmoothiesReader = new InputStreamReader(fSmoothiesStream);
        BufferedReader fSmoothiesBuffer = new BufferedReader(fSmoothiesReader);
        String line = fSmoothiesBuffer.readLine();
        while(line != null){
            String[] newSmoothieParts = line.split(":");
            String newSmoothieName = newSmoothieParts[0];
            String[] newSmoothieIngredients = newSmoothieParts[1].split(",");
            Smoothie newSmoothie = new Smoothie(newSmoothieName, newSmoothieIngredients);
            Ingredients.smoothies.put(newSmoothie.getName(), newSmoothie);
            line = fSmoothiesBuffer.readLine();
        }
        fSmoothiesBuffer.close();
    }

}