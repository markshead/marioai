package ch.idsia.scenarios.oldscenarios;

import ch.idsia.agents.Agent;
import ch.idsia.agents.AgentsPool;
import ch.idsia.agents.learning.SimpleMLPAgent;
import ch.idsia.evolution.Evolvable;
import ch.idsia.evolution.ea.ES;
import ch.idsia.maibe.tasks.MultiSeedProgressTask;
import ch.idsia.mario.engine.GlobalOptions;
import ch.idsia.tools.CmdLineOptions;
import wox.serial.Easy;

/**
 * Created by IntelliJ IDEA.
 * User: julian
 * Date: May 4, 2009
 * Time: 4:33:25 PM
 */
public class EvolveIncrementally
{

    final static int generations = 100;
    final static int populationSize = 100;


    public static void main(String[] args)
    {
        CmdLineOptions options = new CmdLineOptions(new String[0]);
//        options.setNumberOfTrials(1);
        options.setPauseWorld(false);
        Evolvable initial = new SimpleMLPAgent();
        if (args.length > 0)
        {
            initial = (Evolvable) AgentsPool.load(args[0]);
        }
//        AgentsPool.registerAgent ((Agent) initial);
        // maybe need
        AgentsPool.addAgent((Agent) initial);
        for (int difficulty = 0; difficulty < 11; difficulty++)
        {
            System.out.println("New EvolveIncrementally phase with difficulty = " + difficulty + " started.");
            options.setLevelDifficulty(difficulty);
            options.setFPS(GlobalOptions.MaxFPS);
            options.setVisualization(false);
            //Task task = new ProgressTask(options);
            MultiSeedProgressTask task = new MultiSeedProgressTask(options);
            task.setNumberOfSeeds(3);
            task.setStartingSeed(0);
            ES es = new ES(task, initial, populationSize);
            System.out.println("Evolving " + initial + " with task " + task);
            for (int gen = 0; gen < generations; gen++)
            {
                es.nextGeneration();
                double bestResult = es.getBestFitnesses()[0];
                System.out.println("Generation " + gen + " best " + bestResult);
                options.setVisualization(gen % 5 == 0 || bestResult > 4000);
//                options.setFPS(true);
                Agent a = (Agent) es.getBests()[0];
                a.setName(((Agent) initial).getName() + gen);
//                AgentsPool.addAgent(a);
//                AgentsPool.setCurrentAgent(a);
                double result = task.evaluate(a)[0];
                options.setVisualization(false);
//                options.setFPS(true);
                Easy.save(es.getBests()[0], "evolved.xml");
                if (result > 4000)
                {
                    initial = es.getBests()[0];
                    break; // Go to next difficulty.
                }
            }
        }
        System.exit(0);
    }
}
