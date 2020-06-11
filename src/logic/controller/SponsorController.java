package logic.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.SponsorBean;
import logic.dao.SponsorDao;
import logic.model.Sponsor;
import logic.model.User;

/* singleton class
 * */

public class SponsorController {
	private static SponsorController i;
	private User u;
	private Sponsor s;
	private SponsorBean bean = new SponsorBean();
    
    private double price = 0;
	
    //synchronized
	public static synchronized SponsorController getInstance() {
        if (i == null) 
            i = new SponsorController();
        return i;
    }
	
	private SponsorController() {
    }
	
	public SponsorBean calculatePricePreview(SponsorBean beanP) {
		double pr = 5;
    	Date future = null;
    	int type = 0;
    	LocalDate actual = null;
    	
    	String tp = beanP.getType();
    	String tm = beanP.getTime();
    	
    	int k = 0;
    	if( tm.equals("1 month") ) {
    		pr = pr * 30;
    		k = 1;
    	}
    	else if(tm.equals("2 months (-10%)")) {
    		k = 2;
    		pr = 4.5 * 60;
    	}
    	else if(tm.equals("3 months (-15%)")) {
    		k = 3;
    		pr = 4.25 * 90;
    	}
    	else if(tm.equals("6 months (-20%)")) {
    		k = 6;
    		pr = 4 * 180;
    	}
    	else if(tm.equals("1 year (-30%)")) {
    		k = 12;
    		pr = 3.5 * 365;
    	}
    	else {
    		k = 0;
    		pr = 0;
    	}
    	
    	if(k != 0) {
    		actual = LocalDate.now().plusMonths(k);
    	}
    	else {
    		actual = null;
    	}
    	
    	future = java.sql.Date.valueOf(actual);
    	beanP.setTimeline(future);
    	
    	if( tp.equals("Profile")) {
    		pr = pr*1.5;
    		type = 1;
    	}
    	else if( tp.equals("Post")) {
    		//do nothing
    		type = 2;
    	}
    	else if( tp.equals("Both")) {
    		pr = pr*1.8;
    		type = 3;
    	}
    	else {
    		pr = 0;
    		type = 0;
    	}
    	
    	beanP.setTyping(type);
    	beanP.setPrice(pr);
    	return beanP;
    }
    
    public boolean paymentProbab() {
    	double random = Math.random() * 100;
    	Logger logger = Logger.getLogger( SponsorController.class.getName()); 
		logger.log(Level.SEVERE, "Something went wrong: {0} ", random);
        return(random < 80);
    }
    
    public void searchExistingSponsor() {
    	SponsorDao.findSponsorByUsername(this.bean);	

    }
    
    public SponsorBean searchExistingS(SponsorBean b) {
    	String usernameSponsor = b.getUserSponsor();
    	Sponsor sp = SponsorDao.tryFindSponsorByUsername(usernameSponsor);
    	SponsorBean sb = new SponsorBean();
		sb.setUserSponsor(usernameSponsor);
    	if(sp.getTimeline() != null ) {
    		sb.setTypeSponsor(sp.getType()); 
    		sb.setTimeSponsor(sp.getTimeline());
    		
    	}
    	else {
    		sb.setTypeSponsor(0); 
    		sb.setTimeSponsor(null);
    	}
    	
    	return sb;
    }

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public SponsorBean getBean() {
		return this.bean;
	}

	public void setBean(SponsorBean bean) {
		this.bean = bean;
	}
	
	public boolean saveSponsor() {
		LoginController c = LoginController.getInstance();
		Sponsor spo = new Sponsor(c.getBean().getUsername(), this.bean.getTypeSponsor(), this.bean.getTimeline());
		return SponsorDao.setNewSponsor(spo);
	}
	
	public void cleanSponsorDB() {
		SponsorDao.deleteSponsor();
	}

	public Sponsor getS() {
		return s;
	}

	public void setS(Sponsor s) {
		this.s = s;
	}

	public User getUser() {
		return u;
	}

	public void setUser(User u) {
		this.u = u;
	}
}
