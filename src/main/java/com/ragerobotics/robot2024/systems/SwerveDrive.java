package com.ragerobotics.robot2024.systems;

import com.ragerobotics.robot2024.Robot;

public class SwerveDrive implements ISystem {
    private static SwerveDrive instance;

    public static SwerveDrive getInstance() {
        if (instance == null) {
            instance = new SwerveDrive();
        }

        return instance;
    }

    private SwerveDrive() {

    }

    @Override
    public void onUpdate(double timestamp, Robot.Mode mode) {

    }
}
