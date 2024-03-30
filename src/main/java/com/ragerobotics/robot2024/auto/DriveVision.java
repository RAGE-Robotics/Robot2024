package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.PidController;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.systems.SwerveDrive;
import com.ragerobotics.robot2024.systems.SwerveDrive.Mode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class DriveVision implements ITask {
    private PidController m_pidRot = new PidController(Constants.kVisionP, Constants.kVisionI, Constants.kVisionD,
            Constants.kDt);

    public void onStart(double timestamp) {

    }

    public void onUpdate(double timestamp) {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        NetworkTableEntry tx = table.getEntry("tx");
        double rot = m_pidRot.update(Constants.kVisionOffset, tx.getDouble(0));

        SwerveDrive.getInstance().set(Mode.Velocity, 0, 0, rot);
    }

    public boolean isDone() {
        return false;
    }

    public void onStop() {
        SwerveDrive.getInstance().set(Mode.Disabled, 0, 0, 0);
    }
}
