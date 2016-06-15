package view.p2pdialogue.request;

import java.util.List;

import client.view.ViewInterface;
import model.market.OnSaleItem;

public class RequestWhichItemTouBuy implements Request {

	private static final long serialVersionUID = -7887621821680475222L;
	private List<OnSaleItem> items;

	public RequestWhichItemTouBuy(List<OnSaleItem> items) {
		this.items = items;
	}

	@Override
	public void execute(ViewInterface view) {
		// TODO Auto-generated method stub

	}

}
