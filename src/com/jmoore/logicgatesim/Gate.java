package com.jmoore.logicgatesim;

public class Gate {
    Gate gate;
    int id;
    Gate() {
        //Default constructor
    }

    Gate(int id, int gate_id, int x, int y, int orientation) { //0 = NOT; 1 = AND; 2 = NAND; 3 = OR; 4 = NOR; 5 = XOR; 6 = XNOR; 7 = wire;
        this.id = id;
        switch(gate_id) {
            case 0:
                gate = new Gate_NOT(x,y);
                break;
            case 1:
                gate = new Gate_AND(x,y);
                break;
            case 2:
                gate = new Gate_NAND(x,y);
                break;
            case 3:
                gate = new Gate_OR(x,y);
                break;
            case 4:
                gate = new Gate_NOR(x,y);
                break;
            case 5:
                gate = new Gate_XOR(x,y);
                break;
            case 6:
                gate = new Gate_XNOR(x,y);
                break;
            case 7:
                gate = new Gate_Wire(x,y, orientation);
            default:

        }
    }

    void poll() {
        gate.poll();
    }
}
