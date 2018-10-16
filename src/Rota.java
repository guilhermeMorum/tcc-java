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

    public Rota(Rota rota){
        this.base = rota.base;
        this.limit = rota.limit;
        this.carros = rota.carros;
    }

    public List<Carro> getCarros(){
        return carros;
    }

    public int getPeso(){
        return carros.size() > limit ? base * (carros.size()/2) : base;
    }

    public int getBase(){
        return base;
    }

    public int getLimit(){
        return limit;
    }

    public void addCar(Carro carro){
        this.carros.add(carro);
    }

    public void zerarCarros(){
        this.carros = new ArrayList<>();
    }

    public static Rota random(){
        Random rn = new Random();
        return new Rota(rn.nextInt(6)+1, rn.nextInt(41));
    }

}
