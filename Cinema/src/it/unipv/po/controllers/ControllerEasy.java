package it.unipv.po.controllers;

import it.unipv.po.model.cinema.Cinema;
import it.unipv.po.view.ViewCashier;
import it.unipv.po.view.ViewLogin;
import it.unipv.po.view.ViewManager;

public class ControllerEasy {

	private ViewLogin viewLogin;
	private ViewManager viewManager;
	private ViewCashier viewCashier;
	private Cinema model;
	
	
	public ControllerEasy(ViewLogin viewLogin, ViewManager viewManager, ViewCashier viewCashier, Cinema model) {
		this.viewLogin = viewLogin;
		this.viewManager = viewManager;
		this.viewCashier = viewCashier;
		this.model = model;
		
		
	}
	
	public boolean accessSystem() {
		
		return false;
	}
	
	
	
	
	
	
}
