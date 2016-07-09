package server.control.instruction.update;

import client.model.ModelInterface;

public class NotifyMarketEnded implements Update {

	private static final long serialVersionUID = -1320281351081249459L;

	@Override
	public void execute(ModelInterface model) {
		model.endMarket();
	}

}
