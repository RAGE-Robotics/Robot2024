package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.Dropper;

public class RunShooter implements ITask {
    public void onStart(double timestamp) {
        Dropper.getInstance().Shoot();
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
