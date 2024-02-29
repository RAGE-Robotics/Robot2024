package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.SwerveDrive;
import com.ragerobotics.robot2024.systems.SwerveDrive.Mode;

public class StopDrive implements ITask {
    public void onStart(double timestamp) {
        SwerveDrive.getInstance().set(Mode.Disabled, 0, 0, 0);
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
