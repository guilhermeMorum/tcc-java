import java.util.ArrayList;
import java.util.List;

public class Simulacao {

    public static void main(String[] args) {

        int num_rotas = 8;
        int num_carros = 100;
        int num_deuses = 300;
        int num_geracoes = 100000;

        //Criação da população
        List<Rota> rotas = new ArrayList<>();
        for(int i = 0; i < num_rotas; i++){
            rotas.add(Rota.random());
        }
        List<Deus> deuses = new ArrayList<>();
        for(int i = 0; i < num_deuses; i++){
            Deus deus = Deus.fromPopulation(num_carros, rotas);
            deus.generateGenes();
            deuses.add(deus);
        }

        for(int i = 0; i < num_geracoes; i++) {

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
            deuses.remove(getQuemMereceMorrer(deuses));
            deuses.remove(getQuemMereceMorrer(deuses));
            deuses.add(miniDeus);
            deuses.add(miniDeusa);

            if(i%(num_geracoes/10) == 0) {
                mostraDados(deuses, kratos);
            }

        }

        System.out.println();

    }

    public static void mostraDados(List<Deus> deuses, Deus fittest){
        int total = 0;
        StringBuilder result = new StringBuilder();
        for(Rota rota : fittest.getRotas()){
            result.append("Base: ");
            result.append(rota.getBase());
            result.append(" Limite: ");
            result.append(rota.getLimit());
            result.append(" Peso: ");
            result.append(rota.getPeso());
            result.append("\n");
            result.append("========================================================================================================================================================================\n");
            for(Carro carro : rota.getCarros()){
                result.append("<8=8> ");
            }
            result.append("\n========================================================================================================================================================================\n");
        }
        for(Deus deus : deuses){
            total += deus.getTotalFitness();
        }
        System.out.println("Generation Fitness: "+total);
        System.out.println(result);
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
            if(deus.getTotalFitness() > kratos.getTotalFitness() && deus.getTotalFitness() < atena.getTotalFitness()){
                atena = deus;
            }
        }
        return atena;
    }

}
