package com.jmoore.logicgatesim;

public class Gate_Wire extends Gate {
    int x, y;
    int in = 0, out = 0;
    int orientation;
    // 1 = ->
    // 2 = ^
    // 3 = <-
    // 4 = \/

    Gate_Wire(int x, int y, int orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    void poll() {
        switch(orientation) {
            case 1:
                if(Core.ui.check_coords(x - 1,y) == 1) out = 1;
                else out = 0;
                break;
            case 2:
                if(Core.ui.check_coords(x,y - 1) == 1) out = 1;
                else out = 0;
                break;
            case 3:
                if(Core.ui.check_coords(x + 1,y) == 1) out = 1;
                else out = 0;
                break;
            case 4:
                if(Core.ui.check_coords(x,y + 1) == 1) out = 1;
                else out = 0;
                break;
        }
        Core.ui.update_grid(x,y,out);
    }
}
