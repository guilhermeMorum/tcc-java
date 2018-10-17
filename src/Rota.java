import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rota {

    private int base;
    private int limit;
    private String name;

    private List<Carro> carros = new ArrayList<>();

    public Rota(int base, int limit){
        this.base = base;
        this.limit = limit;
    }

    public Rota(String name, int base, int limit){
        this.name = name;
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
        if(carros.size() == 0){
            return 0;
        }
        return carros.size() > limit ? base * (carros.size()/2) : base;
    }

    public int getBase(){
        return base;
    }

    public int getLimit(){
        return limit;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append(" Nome: ");
        result.append(this.getName());
        result.append(" Base: ");
        result.append(this.getBase());
        result.append(" Limite: ");
        result.append(this.getLimit());
        result.append(" Peso: ");
        result.append(this.getPeso());
        result.append("\n");
        result.append("========================================================================================================================================================================\n");
        for(Carro carro : this.getCarros()){
            result.append("<8=8> ");
        }
        result.append("\n========================================================================================================================================================================\n");
        return result.toString();
    }

}
