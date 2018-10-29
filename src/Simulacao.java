import java.util.ArrayList;
import java.util.List;

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

        Deus kratos = null, atena = null, miniDeus = null, miniDeusa = null;
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

            if(i%show_frequency == 0){
                mostraDados(miniDeus, miniDeusa, logMode, kratos, atena, deuses);
            }

            lastGenerations[i%num_geracoes] = Deus.getFitness(deuses);

        }

        mostraDados(miniDeus, miniDeusa, logMode, kratos, atena, deuses);

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
                    System.out.printf("Base: %d; Limite: %d; Peso: %d; QntdCarros: %d;\n", rota.getBase(), rota.getLimit(), rota.getPeso(), rota.getCarros().size());
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
