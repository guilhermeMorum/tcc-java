import java.util.ArrayList;
import java.util.List;

public class Simulacao {

    public static void main(String[] args) {

        //Criação da população
        List<Deus> deuses = new ArrayList<>();
        for(int i = 0; i < 150; i++){
            Deus deus = Deus.fromPopulation(100, 8);
            deus.generateGenes();
            deuses.add(deus);
        }

        for(int i = 0; i < 100000; i++) {

            for(Deus deus : deuses){
                deus.distributeCars();
            }

            //Seleção
            Deus kratos = getKratos(deuses);
            Deus atena = getAtena(deuses, kratos);

            //CrossOver
            Deus miniDeus = Deus.fromParent(kratos);
            Deus miniDeusa = Deus.fromParent(atena);
            int[] cross1 = new int[kratos.getGenes().length];
            int[] cross2 = new int[kratos.getGenes().length];
            Deus.crossOver(kratos, atena, cross1, cross2);
            miniDeus.setGenes(cross1);
            miniDeusa.setGenes(cross2);

            //Mutação
            miniDeus.mutate();
            miniDeusa.mutate();

            //Atualizar população
            deuses.add(miniDeus);
            deuses.add(miniDeusa);
            deuses.remove(kratos);
            deuses.remove(atena);

            totalFitness(deuses);

        }

        System.out.println();

    }

    public static void totalFitness(List<Deus> deuses){
        int total = 0;
        for(Deus deus : deuses){
            total += deus.getTotalFitness();
        }
        System.out.println(total);
    }

    public static Deus getKratos(List<Deus> deuses){
        Deus kratos = deuses.get(0);
        for(Deus deus : deuses){
            if(deus.getTotalFitness() < kratos.getTotalFitness()){
                kratos = deus;
            }
        }
        return kratos;
    }

    public static Deus getAtena(List<Deus> deuses, Deus kratos){
        Deus atena = deuses.get(0);
        for(Deus deus : deuses){
            if(deus.getTotalFitness() > kratos.getTotalFitness() && deus.getTotalFitness() < atena.getTotalFitness()){
                atena = deus;
            }
        }
        return atena;
    }

}
