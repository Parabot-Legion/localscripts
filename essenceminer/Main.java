package localscripts.essenceminer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;

@ScriptManifest(author = "Legion", category = Category.MINING, description = "Mines pure essence", name = "Pure Essence Miner, start at rune essence location", servers = {
		"Ikov" }, version = 1)
public class Main extends Script implements MessageListener, Paintable {
	private Timer time = new Timer();

	private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();

	public boolean onExecute() {
		strategies.add(new MineEssence());
		strategies.add(new BankEssence());
		provide(strategies);
		return true;
	}

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.gray);
		g.setFont(new Font("courier", Font.ITALIC, 17));
		g.drawString("Essence Miner", 550, 290);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.ITALIC, 14));

		g.drawString("Run Time -  " + time, 550, 310);
		g.drawString("Rune Count -  " + Constants.RUNE_COUNT, 550, 346);
		g.drawString("Trips Made -  " + Constants.TRIP_COUNT, 550, 327);
		g.drawString("- Legion", 550, 365);
	}

	public void messageReceived(MessageEvent i) {
		if (i.getMessage().contains("get some")) {
			Constants.RUNE_COUNT++;
		}

	}
}
