import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rota {

    private int base;
    private int limit;

    private List<Carro> carros = new ArrayList<>();

    public Rota(int base, int limit){
        this.base = base;
        this.limit = limit;
    }

    public List<Carro> getCarros(){
        return carros;
    }

    public int getPeso(){
        return carros.size() > limit ? base * carros.size() : base;
    }

    public void addCar(Carro carro){
        carros.add(carro);
    }

    public void zerarCarros(){
        this.carros = new ArrayList<>();
    }

    public static Rota random(){
        Random rn = new Random();
        return new Rota(rn.nextInt(6)+1, rn.nextInt(41));
    }

}