/* Genetic algorithm for crystal structure prediction.  Will Tipton.  Ceder Lab at MIT. Summer 2007 */

package ga;

import utility.ArgumentParser;
import utility.Utility;
import crystallography.Cell;


// Contains the top-level call for the crystal structure prediction program
// and a lot of the chemistry knowledge and logic.

// TODO: take a stoichiometry and return a Structure (long term goal.)

public class CrystalGA {

	// To seed the initial population, pass it in and have the input file use the
	// "manual" population option (e.g. population 20 structures manual).
	// When using a random initial population, the value of initialPop doesn't matter.
	public static Cell crystalGA(String inputFileName, Cell[] initialPop) {
		GAParameters params = GAParameters.getParams();
		String[] args = {"--f ", inputFileName};
		params.setArgs(args);

		params.setSeedGeneration(initialPop);
		
		StructureOrg s = (StructureOrg)GeneticAlgorithm.doGeneticAlgorithm();
		
		return s.getCell();
	}
	
	public static void main(String[] args) {
		// if no args, print usage statement and exit
		if (args.length == 0)
			GAParameters.usage("", true);
		
		// Parse command-line arguments
		
		ArgumentParser aParser = new ArgumentParser(args);
		if (aParser.hasArguments("seed"))
			GAParameters.getParams().getRandom().setSeed(Long.parseLong(aParser.getArgument("seed")));

		if (aParser.hasArguments("r")) {
			System.out.println("Resuming from " +  aParser.getArgument("r"));
			GAParameters.setParams((GAParameters)(Utility.readSerializable(aParser.getArgument("r"))));
//			GAParameters.getParams().getRandom().setSeed(System.currentTimeMillis());
		} else if (aParser.hasArguments("rc")) {
			System.out.println("Resuming from " +  aParser.getArgument("rc") + " and clearing convergence criteria.");
			GAParameters.setParams((GAParameters)(Utility.readSerializable(aParser.getArgument("rc"))));
//			GAParameters.getParams().getRandom().setSeed(System.currentTimeMillis());
			GAParameters.getParams().getCCs().clear();
		} else {
			GAParameters.getParams().setArgs(args);
		}
		
		GeneticAlgorithm.doGeneticAlgorithm();

	}
}
