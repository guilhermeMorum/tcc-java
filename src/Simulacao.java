import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulacao {

    public static void main(String[] args) {

        String logMode = null;

        if(args != null && args.length > 0){
            switch(args[0]){
                case "melhor":
                case "rotas":
                case "todos":
                    logMode = args[0];
                default: break;
            }
        }

        int num_rotas = 8;
        int num_carros = 375;
        int num_populacao = 1000;
        int num_geracoes = 50;
        int show_frequency = 100;

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

        Deus parent1 = null, parent2 = null, child1 = null, child2 = null;
        int[] lastGenerations = new int[num_geracoes];
        lastGenerations[0] = 1;

        int i;
        //Processo de seleção
        for(i = 0; !converged(lastGenerations); i++) {

            //Distribuição dos carros de acordo com os genes
            for(Deus deus : deuses){
                deus.distributeCars();
            }

            //Seleção
            Deus.calculateFitness(deuses);
            parent1 = select(deuses);
            parent2 = select(deuses, parent1);

            //CrossOver
            child1 = Deus.fromParent(parent1);
            child2 = Deus.fromParent(parent2);
            int[] cross1 = new int[parent1.getGenes().length];
            int[] cross2 = new int[parent1.getGenes().length];
            Deus.crossOver(parent1, parent2, cross1, cross2);
            child1.setGenes(cross1);
            child2.setGenes(cross2);

            //Mutação
            child1.mutate();
            child2.mutate();

            //Atualizar população
            deuses.remove(getLeastFittest(deuses));
            deuses.remove(getLeastFittest(deuses));
            deuses.add(child1);
            deuses.add(child2);

            if(i%show_frequency == 0){
                mostraDados(child1, child2, logMode, parent1, parent2, deuses);
            }

            lastGenerations[i%num_geracoes] = Deus.getFitness(deuses);

        }

        mostraDados(child1, child2, logMode, parent1, parent2, deuses);

        System.out.println("Solução encontrada na geração: "+i);

    }

    public static boolean converged(int[] generations){
        for(int fitness : generations){
            for(int fitness2 : generations){
                if(fitness != fitness2){
                    return false;
                }
            }
        }
        return true;
    }

    public static void log(String logMode, Object... params){
        System.out.println("----------");
        switch(logMode){
            case "melhor":
                System.out.println(((Deus) params[0]).getTotalFitness());
                break;
            case "rotas":
                List<Rota> rotas = (List<Rota>) params[0];
                for(Rota rota: rotas){
                    System.out.printf("Base: %d; Limite: %d; Peso: %d; QntdCarros: %d;\n", rota.getBase(), rota.getLimit(), rota.getPeso(), rota.getVeiculos().size());
                }
                break;
            case "todos":
                List<Deus> deuses = (List<Deus>) params[0];
                int totalFitness = 0;
                for(Deus deus: deuses){
                    totalFitness += deus.getTotalFitness();
                }
                System.out.println(totalFitness);
                break;
            default: break;
        }
    }

    public static void mostraDados(Deus kratos, Deus atena, Deus miniDeus, Deus miniDeusa){
        System.out.println("Kratos Fitness: "+kratos.getTotalFitness() + " Atena Fitness: "+atena.getTotalFitness());
        System.out.println("MiniDeus Fitness: "+miniDeus.getTotalFitness() + " MiniDeusa Fitness: "+miniDeusa.getTotalFitness());
        for(Rota rota : kratos.getRotas()){
            System.out.println(rota);
        }
    }

    public static void mostraDados(Deus miniDeus, Deus miniDeusa, String logMode, Deus kratos, Deus atena, List<Deus> deuses){
        miniDeus.distributeCars();
        miniDeusa.distributeCars();
        Deus.calculateFitness(miniDeus, miniDeusa);
        if(logMode == null) {
            mostraDados(kratos, atena, miniDeus, miniDeusa);
        } else {
            switch(logMode){
                case "melhor":
                    log(logMode, kratos);
                    break;
                case "rotas":
                    log(logMode, kratos.getRotas());
                    break;
                case "todos":
                    log(logMode, deuses);
                default: break;
            }
        }
    }

    public static Deus getLeastFittest(List<Deus> deuses){
        Deus morto = deuses.get(0);
        for(Deus deus : deuses){
            if(deus.getTotalFitness() > morto.getTotalFitness()){
                morto = deus;
            }
        }
        return morto;
    }

    public static Deus select(List<Deus> deuses){
        long totalFitness = 0L;
        for(Deus deus : deuses){
            totalFitness += deus.getTotalFitness();
        }
        Long[] individualFitness = new Long[deuses.size()];
        Long lastValue = 0L;
        for(int i = 0; i < individualFitness.length; i++){
            long valor = totalFitness - deuses.get(i).getTotalFitness();
            individualFitness[i] = lastValue + (valor*100/totalFitness);
            lastValue = individualFitness[i];
        }
        int randomSelection = new Random().nextInt(lastValue.intValue());
        for(int i = 0; i < individualFitness.length; i++) {
            if(randomSelection < individualFitness[i]){
                return deuses.get(i);
            }
        }
        return null;
    }

    public static Deus select(List<Deus> deuses, Deus deus){
        Deus selected = null;
        do {
            selected = select(deuses);
        } while (selected.equals(deus));
        return selected;
    }

}
