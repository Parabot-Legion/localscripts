package localscripts.essenceminer;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.SceneObjects.Option;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.SceneObject;

public class BankEssence implements Strategy {

	@Override
	public boolean activate() {

		return !AreaInformation.AT_RUNE_ESSENCE.contains(Players.getMyPlayer().getLocation());
	}

	@Override
	public void execute() {
		SceneObject[] bank = SceneObjects.getNearest(6084);
		Npc[] aubury = Npcs.getNearest(553);
		if (Players.getMyPlayer().getLocation().equals(Constants.TELED_OUT) && Inventory.isFull()) {
			Constants.TO_BANK.walkTo();
			Time.sleep(new SleepCondition() {
				@Override
				public boolean isValid() {
					return Players.getMyPlayer().getLocation().equals(Constants.TO_BANK);
				}
			}, 3000);
		} else {
			if (Players.getMyPlayer().getLocation().equals(Constants.TELED_OUT) && Inventory.isEmpty()) {
				if (aubury != null && aubury.length > 0) {
					aubury[0].interact(3);
					Time.sleep(new SleepCondition() {
						@Override
						public boolean isValid() {
							return AreaInformation.AT_RUNE_ESSENCE.contains(Players.getMyPlayer().getLocation());
						}
					}, 3000);

				}

			}
		}
		if (Inventory.isFull()) {
			if (AreaInformation.IN_BANK.contains(Players.getMyPlayer().getLocation())) {
				if (!Bank.isOpen()) {
					if (bank != null && bank.length > 0) {
						bank[0].interact(Option.USE_QUICKLY);
						Time.sleep(new SleepCondition() {
							@Override
							public boolean isValid() {
								return Bank.isOpen();
							}
						}, 3000);

					}
				} else if (Bank.isOpen()) {
					// Bank.depostAll Isnt fixed of yet
					Bank.depositAllExcept();
					Time.sleep(500);
					if (Inventory.isEmpty()) {
						Constants.TRIP_COUNT++;
						Bank.close();
						Time.sleep(new SleepCondition() {
							@Override
							public boolean isValid() {
								return !Bank.isOpen();
							}
						}, 3000);

					}

				}

			}
		} else if (!Inventory.isFull() && AreaInformation.IN_BANK.contains(Players.getMyPlayer().getLocation())
				&& !Bank.isOpen()) {
			Constants.TELED_OUT.walkTo();
			Time.sleep(new SleepCondition() {
				@Override
				public boolean isValid() {
					return Players.getMyPlayer().getLocation().equals(Constants.TELED_OUT);
				}
			}, 3000);
		}

	}

}
