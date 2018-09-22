import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deus {

    private List<Carro> carros = new ArrayList<>();
    private List<Rota> rotas = new ArrayList<>();

    private final int population;
    private final int routes;

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public List<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(List<Rota> rotas) {
        this.rotas = rotas;
    }

    public void setTotalFitness(int totalFitness) {
        this.totalFitness = totalFitness;
    }

    public int getPopulation() {
        return population;
    }

    public int getRoutes() {
        return routes;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    private int totalFitness = 0;

    private int[] genes;

    private Deus(int pupolation, int routes){
        this.population = pupolation;
        this.routes = routes;
        this.genes = new int[population];
        this.addCars();
        this.addRoutes();
    }

    private Deus(Deus kratos){
        this.population = kratos.getPopulation();
        this.routes = kratos.getRoutes();
        this.genes = kratos.getGenes().clone();
        this.carros = kratos.getCarros();
        this.rotas = kratos.getRotas();
    }

    private void addCars(){
        for(int i = 0; i < population; i++){
            this.carros.add(new Carro());
        }
    }

    private void addRoutes(){
        for(int i = 0; i < routes; i++){
            this.rotas.add(Rota.random());
        }
    }

    public static Deus fromPopulation(int population, int routes){
        return new Deus(population, routes);
    }

    public static Deus fromParent(Deus kratos){
        Deus deus = new Deus(kratos);
        return deus;
    }

    public static void crossOver(Deus kratos, Deus atena, int[] genes1, int[] genes2){
        int crossPoint = new Random().nextInt(kratos.genes.length);
        cloneArray(genes1, kratos.getGenes());
        for(int i = 0; i < crossPoint; i++){
            genes1[i] = atena.getGenes()[i];
        }
        cloneArray(genes2, atena.getGenes());
        for(int i = 0; i < crossPoint; i++){
            genes2[i] = kratos.getGenes()[i];
        }
    }

    private static void cloneArray(int[] array, int[] toClone){
        for(int i = 0; i < toClone.length; i++){
            array[i] = toClone[i];
        }
    }

    public void distributeCars(){
        for(Rota rota: rotas){
            rota.zerarCarros();
        }
        for(int i = 0; i < population; i++){
            rotas.get(genes[i]).addCar(carros.get(i));
        }
    }

    public void generateGenes(){
        Random rn = new Random();
        for(int i = 0; i < genes.length; i++){
            genes[i] = rn.nextInt(routes);
        }
    }

    private void calculateFitness(){
        totalFitness = 0;
        for(Rota rota : rotas){
            totalFitness += rota.getPeso();
        }
    }

    public int getTotalFitness(){
        calculateFitness();
        return totalFitness;
    }

    public void mutate(){
        Random rn = new Random();
        for(int i = 0; i < genes.length; i++) {
            if (rn.nextInt(100) == 1) {
                genes[rn.nextInt(genes.length)] = rn.nextInt(routes);
            }
        }
    }

}