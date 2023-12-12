package com.sample;
import java.util.Scanner;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
        	
        	Interface inter =  new Interface();
        	kSession.setGlobal("inter", inter);
        	
            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            kSession.insert(message);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;
        public static final int aaa = 2;        
        public static final int bbb = 3;
        
        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }
    
    public static class Interface {
    	Scanner in = new Scanner(System.in);
    	public int getOpt() {
    		int num = in.nextInt();
    		return num;
    	}
    	public void showOpt(String [] opts) {
    		int i = 0;
    		for (String o: opts) {
    			System.out.print(i);
    			System.out.print(" : ");
    			System.out.println(o);
    			i = i + 1;
    		}
    		System.out.println();
    	}
    	public void showQ(String s) {
    		System.out.println(s);
    	}
    }

}
