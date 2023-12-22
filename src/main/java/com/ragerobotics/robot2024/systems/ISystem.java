package com.ragerobotics.robot2024.systems;

import com.ragerobotics.robot2024.Robot;

public interface ISystem {
    public void onUpdate(double timestamp, Robot.Mode mode);
    public boolean isOkay();
}
