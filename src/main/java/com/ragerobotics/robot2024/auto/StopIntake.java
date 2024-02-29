package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.Dropper;
import com.ragerobotics.robot2024.systems.Intake;

public class StopIntake implements ITask {
    public void onStart(double timestamp) {
        Intake.getInstance().intake(0);
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
