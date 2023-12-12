package com.shakespeare;

import org.kie.api.KieServices;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import com.shakespeare.ShakespeareInterface;

public class ShakespeareMain {
	static ShakespeareFrame sf;
	public static void main(String[] args) {
		 sf = new ShakespeareFrame();
	}
}
