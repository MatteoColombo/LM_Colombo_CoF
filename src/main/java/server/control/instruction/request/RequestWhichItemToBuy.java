package server.control.instruction.request;

import java.util.List;

import client.control.ViewInterface;
import client.model.ModelInterface;
import server.control.instruction.update.Update;
import server.model.market.OnSaleItem;

public class RequestWhichItemToBuy implements Update, Request{

	private static final long serialVersionUID = -7887621821680475222L;
	private List<OnSaleItem> items;

	public RequestWhichItemToBuy(List<OnSaleItem> items) {
		this.items = items;
	}

	@Override
	public void execute(ModelInterface model) {
		model.setMarket(items);		
	}

	@Override
	public void execute(ViewInterface view) {
		// We don't need to to any action here
	}

}
