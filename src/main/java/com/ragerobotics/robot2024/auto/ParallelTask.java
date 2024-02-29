package com.ragerobotics.robot2024.auto;

public class ParallelTask implements ITask {
    private ITask[] m_tasks;

    public ParallelTask(ITask... tasks) {
        m_tasks = tasks;
    }

    public void onStart(double timestamp) {
        for (ITask task : m_tasks) {
            task.onStart(timestamp);
        }
    }

    public void onUpdate(double timestamp) {
        for (ITask task : m_tasks) {
            task.onUpdate(timestamp);
        }
    }

    public boolean isDone() {
        for (ITask task : m_tasks) {
            if (!task.isDone()) {
                return false;
            }
        }

        return true;
    }

    public void onStop() {
        for (ITask task : m_tasks) {
            task.onStop();
        }
    }
}
