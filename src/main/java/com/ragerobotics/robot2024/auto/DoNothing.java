package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.SwerveDrive;
import com.ragerobotics.robot2024.systems.SwerveDrive.Mode;

public class DoNothing implements ITask {
    public void onStart(double timestamp) {

    }

    public void onUpdate(double timestamp) {
        SwerveDrive.getInstance().set(SwerveDrive.Mode.Disabled, 0, 0, 0);
    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
