package server.control.dialogue.request;

import java.util.List;

import client.control.ViewInterface;
import client.model.ModelInterface;
import client.viewGUI.model.GameProperty;
import server.control.dialogue.update.Update;
import server.model.market.OnSaleItem;

public class RequestWhichItemTouBuy implements Request, Update {

	private static final long serialVersionUID = -7887621821680475222L;
	private List<OnSaleItem> items;

	public RequestWhichItemTouBuy(List<OnSaleItem> items) {
		this.items = items;
	}

	@Override
	public void execute(ViewInterface view) {
		view.printMessage("Select which item you want to buy:");
		for(OnSaleItem item: items)
			view.printMessage((items.indexOf(item)+1)+". "+item.getClass().getSimpleName()+ " "+item.getPrice());
	}

	@Override
	public void execute(ModelInterface model) {
		((GameProperty)model).setMarket(items);
		
	}

}
