package com.shakespeare;

public class ShakespeareInterface {
	public int getOpt() {
		int o = ShakespeareMain.sf.getOptionNum();
		if(o!=-1) {
			ShakespeareMain.sf.resetOpt();
		}
		return o;
	}
	public void showOpt(String[] opts) {
		int i = 0;
		for (String o: opts) {
			
		}
		
	}
	public void showQ(String s) {
		ShakespeareMain.sf.setQuestion(s);
	}
}
