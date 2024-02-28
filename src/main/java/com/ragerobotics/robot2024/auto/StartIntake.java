package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.systems.Dropper;
import com.ragerobotics.robot2024.systems.Intake;

public class StartIntake implements ITask {
    public void onStart(double timestamp) {
        Intake.getInstance().intake(Constants.kIntakeGain);
        Dropper.getInstance().transfer();
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
