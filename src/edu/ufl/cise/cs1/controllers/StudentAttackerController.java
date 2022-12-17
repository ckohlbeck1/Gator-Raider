package edu.ufl.cise.cs1.controllers;
import game.controllers.AttackerController;
import game.models.*;
import java.awt.*;
import java.util.List;

public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		int action;

		Attacker gator = game.getAttacker();
		List<Node> pills = game.getPillList();
		List<Node> powerPills = game.getPowerPillList();
		List<Defender> defenders = game.getDefenders();

		/* Strategy #1 */

		// If distance between attacker and a non vulnerable defender is less than 6, attacker goes away from it
		// iterates through list of defenders
		for(int i = 0; i < defenders.size(); i++) {

			// Find the closest non vulnerable defender and stores it as an Actor variable
			Actor closestNonVulnerableDefender = gator.getTargetActor(defenders, true);

			// Checks if the defender is NOT vulnerable, if it is the closest one to the attacker, and if the distance between it and the attack is less than 6
			if(!defenders.get(i).isVulnerable() && defenders.get(i).equals((Defender)closestNonVulnerableDefender) && gator.getLocation().getPathDistance(defenders.get(i).getLocation()) < 6) {

				// If the 3 conditions above are true, the attacker will go away from that defender
				int nextDir = gator.getNextDir(defenders.get(i).getLocation(), false);
				return nextDir;
			}
		}

		/* Strategy #2 */

		// If defender is vulnerable, then attacker goes toward closest vulnerable defender
		// iterates through list of defenders
		for(int i = 0; i < defenders.size(); i++) {

			// Finds the closest vulnerable defender and stores it as an actor variable
			Actor closestVulnerableDefender = gator.getTargetActor(defenders, true);

			// Checks if the defender is vulnerable and if it is the closest one to the attacker
			if(defenders.get(i).isVulnerable() && defenders.get(i).equals((Defender)closestVulnerableDefender)) {

				// If both conditions are met, the attacker will go toward the defender
				int nextDir = gator.getNextDir(defenders.get(i).getLocation(), true);
				return nextDir;
			}
		}

		/* Strategy #3 */

		// If there is a power pill available, attacker goes toward closest power pill
		// Checks that power pill list is greater than zero and not equal to null, meaning that there are power pills on the game interface
		if(powerPills != null && powerPills.size() > 0) {

			// Finds location of closest power pill and stores that node
			Node closestPowerPill = gator.getTargetNode(powerPills, true);

			// Attacker will go toward the closest power pill
			int nextDir = gator.getNextDir(closestPowerPill, true);
			return nextDir;
		}

		/* Strategy #4 */

		//If there is a pill available, attacker goes toward the closest pill
		// Find location of closest pill and stores that node
		Node closestPill = gator.getTargetNode(pills, true);

		// Attacker will go toward closest pill
		int nextDir = gator.getNextDir(closestPill, true);

		return nextDir;

	}
}