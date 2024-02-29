package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.Dropper;

public class StopDrop implements ITask {
    public void onStart(double timestamp) {
        Dropper.getInstance().dropperStow();
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
