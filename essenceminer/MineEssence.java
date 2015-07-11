package localscripts.essenceminer;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.methods.SceneObjects.Option;
import org.rev317.min.api.wrappers.SceneObject;

public class MineEssence implements Strategy {
	@Override
	public boolean activate() {
		return AreaInformation.AT_RUNE_ESSENCE.contains(Players.getMyPlayer().getLocation());
	}

	@Override
	public void execute() {
		SceneObject[] runeEssence = SceneObjects.getNearest(2491);
		SceneObject[] portal = SceneObjects.getNearest(2492);
		if (!Inventory.isFull()) {
			if (Players.getMyPlayer().getAnimation() == -1) {
				if (runeEssence != null && runeEssence.length > 0) {
					runeEssence[0].interact(Option.MINE);
					Time.sleep(new SleepCondition() {
						@Override
						public boolean isValid() {
							return Players.getMyPlayer().getAnimation() != -1;
						}
					}, 5000);
				}
			}
		} else if (Inventory.isFull()) {
			if (portal != null && portal.length > 0) {
				portal[0].interact(Option.USE);
				Time.sleep(new SleepCondition() {
					@Override
					public boolean isValid() {
						return !AreaInformation.AT_RUNE_ESSENCE.contains(Players.getMyPlayer().getLocation());
					}
				}, 3000);

			}

		}
	}

}
