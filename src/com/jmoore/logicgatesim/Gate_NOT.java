package com.jmoore.logicgatesim;

public class Gate_NOT extends Gate {
    int x, y;
    int in = 0, out = 1;

    Gate_NOT(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void poll() {
        if(Core.ui.check_coords(x - 1, y - 1) == 0) in = 0;
        else if(Core.ui.check_coords(x - 1, y - 1) == 1) in = 1;
        else in = -1;

        if(in == 0) out = 1;
        if(in == 1) out = 0;
        if(in == -1) out = 0;

        Core.ui.update_grid(x + 3, y - 1, out);
    }
}
