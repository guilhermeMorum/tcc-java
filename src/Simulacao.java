import java.util.ArrayList;
import java.util.List;

public class Simulacao {

    public static void main(String[] args) {

        int num_rotas = 8;
        int num_carros = 150;
        int num_populacao = 1000;
        int num_geracoes = 10000;

        //Geração das rotas
        List<Rota> rotas = new ArrayList<>();
        for(int i = 0; i < num_rotas; i++){
            rotas.add(Rota.random());
        }

        //Geração da população
        List<Deus> deuses = new ArrayList<>();
        for(int i = 0; i < num_populacao; i++){
            Deus deus = Deus.withPopulation(num_carros, rotas);
            deus.generateGenes();
            deuses.add(deus);
        }

        Deus kratos = null, atena = null, miniDeus = null, miniDeusa = null;

        //Processo de seleção
        for(int i = 0; i < num_geracoes; i++) {

            //Distribuição dos carros de acordo com os genes
            for(Deus deus : deuses){
                deus.distributeCars();
            }

            //Seleção
            Deus.calculateFitness(deuses);
            kratos = getKratos(deuses);
            atena = getAtena(deuses, kratos);

            //CrossOver
            miniDeus = Deus.fromParent(kratos);
            miniDeusa = Deus.fromParent(atena);
            int[] cross1 = new int[kratos.getGenes().length];
            int[] cross2 = new int[kratos.getGenes().length];
            Deus.crossOver(kratos, atena, cross1, cross2);
            miniDeus.setGenes(cross1);
            miniDeusa.setGenes(cross2);

            //Mutação
            miniDeus.mutate();
            miniDeusa.mutate();

            //Atualizar população
            deuses.remove(getQuemMereceMorrer(deuses));
            deuses.remove(getQuemMereceMorrer(deuses));
            deuses.add(miniDeus);
            deuses.add(miniDeusa);

            if(i == 0){
                miniDeus.distributeCars();
                miniDeusa.distributeCars();
                Deus.calculateFitness(miniDeus, miniDeusa);
                mostraDados(kratos, atena, miniDeus, miniDeusa);
            }

        }

        miniDeus.distributeCars();
        miniDeusa.distributeCars();
        Deus.calculateFitness(miniDeus, miniDeusa);
        mostraDados(kratos, atena, miniDeus, miniDeusa);

    }

    public static void mostraDados(Deus kratos, Deus atena, Deus miniDeus, Deus miniDeusa){
        System.out.println("Kratos Fitness: "+kratos.getTotalFitness() + " Atena Fitness: "+atena.getTotalFitness());
        System.out.println("MiniDeus Fitness: "+miniDeus.getTotalFitness() + " MiniDeusa Fitness: "+miniDeusa.getTotalFitness());
        for(Rota rota : kratos.getRotas()){
            System.out.println(rota);
        }
    }

    public static Deus getQuemMereceMorrer(List<Deus> deuses){
        Deus morto = deuses.get(0);
        for(Deus deus : deuses){
            if(deus.getTotalFitness() > morto.getTotalFitness()){
                morto = deus;
            }
        }
        return morto;
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
            if(deus.getTotalFitness() < atena.getTotalFitness() && deus.getTotalFitness() > kratos.getTotalFitness()){
                atena = deus;
            }
        }
        return atena;
    }

    public static List<Rota> getRotasRomenia(){
        List<Rota> rotas = new ArrayList<>();
        return rotas;
    }

}
