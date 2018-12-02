import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rota {

    private int base;
    private int limit;
    private String name;

    private List<Veiculo> veiculos = new ArrayList<>();

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
        this.veiculos = rota.veiculos;
    }

    public List<Veiculo> getVeiculos(){
        return veiculos;
    }

    public int getPeso(){
        if(veiculos.size() == 0){
            return 0;
        }
        return veiculos.size() > limit ? base*(veiculos.size()-limit+1) : base;
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

    public void addCar(Veiculo veiculo){
        this.veiculos.add(veiculo);
    }

    public void zerarCarros(){
        this.veiculos = new ArrayList<>();
    }

    public static Rota random(){
        Random rn = new Random();
        return new Rota(rn.nextInt(1000)+100, rn.nextInt(90)+10);
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
        for(Veiculo veiculo : this.getVeiculos()){
            result.append("<8=8> ");
        }
        result.append("\n========================================================================================================================================================================\n");
        return result.toString();
    }

}
