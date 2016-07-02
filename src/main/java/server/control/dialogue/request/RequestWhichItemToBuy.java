package server.control.dialogue.request;

import java.util.List;
import client.model.ModelInterface;
import server.control.dialogue.update.Update;
import server.model.market.OnSaleItem;

public class RequestWhichItemToBuy implements Update{

	private static final long serialVersionUID = -7887621821680475222L;
	private List<OnSaleItem> items;

	public RequestWhichItemToBuy(List<OnSaleItem> items) {
		this.items = items;
	}

	@Override
	public void execute(ModelInterface model) {
		model.setMarket(items);
		
	}

}
