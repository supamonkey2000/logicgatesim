package com.jmoore.logicgatesim;

public class Gate_OR extends Gate {
    int x, y;
    int in_1 = 0, in_2 = 0, out = 0;

    Gate_OR(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void poll() {
        if(Core.ui.check_coords(x - 1, y) == 0) in_1 = 0;
        else if(Core.ui.check_coords(x - 1, y) == 1) in_1 = 1;
        else in_1 = -1;

        if(Core.ui.check_coords(x - 1, y - 2) == 0) in_2 = 0;
        else if(Core.ui.check_coords(x - 1, y - 2) == 1) in_2 = 1;
        else in_2 = -1;

        if(in_1 == 0 && in_2 == 0) out = 0;
        else out = 1;

        Core.ui.update_grid(x + 3, y - 1, out);
    }
}
