package com.jmoore.logicgatesim;

public class Poller extends Thread {

    public void run() {
        while(Core.RUNNING) {
            try {
                Thread.sleep(500); //How long to wait before refreshing the gates
            } catch(InterruptedException ex) {
                ex.printStackTrace();
                System.err.println("ERROR.Poller: Could not freeze thread!");
            }
            for(Gate gate : Core.gates) {
                gate.poll();
            }
        }
    }
}
