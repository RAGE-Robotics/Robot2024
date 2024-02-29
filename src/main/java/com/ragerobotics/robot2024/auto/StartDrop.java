package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.Dropper;

public class StartDrop implements ITask {
    public void onStart(double timestamp) {
        Dropper.getInstance().drop();
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
