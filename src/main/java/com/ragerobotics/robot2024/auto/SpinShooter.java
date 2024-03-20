package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.Dropper;

public class SpinShooter implements ITask {
    public void onStart(double timestamp) {
        Dropper.getInstance().ShooterSpinUp();
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
