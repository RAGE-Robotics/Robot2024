package com.ragerobotics.robot2024.auto;

public interface ITask {
    public void onStart(double timestamp);

    public void onUpdate(double timestamp);

    public boolean isDone();

    public void onStop();
}
