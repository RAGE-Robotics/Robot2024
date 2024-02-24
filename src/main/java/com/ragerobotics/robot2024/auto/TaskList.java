package com.ragerobotics.robot2024.auto;

import java.util.ArrayList;

public class TaskList implements ITask {
    private ArrayList<ITask> m_tasks = new ArrayList<ITask>();
    private int m_i = 0;

    public TaskList(ITask... tasks) {
        for (ITask task : tasks) {
            m_tasks.add(task);
        }
    }

    protected void add(ITask task) {
        m_tasks.add(task);
    }

    public void onStart(double timestamp) {
        if (m_tasks.size() > 0) {
            m_tasks.get(0).onStart(timestamp);
        }
    }

    public void onUpdate(double timestamp) {
        if (m_i < m_tasks.size()) {
            m_tasks.get(m_i).onUpdate(timestamp);

            if (m_tasks.get(m_i).isDone()) {
                m_tasks.get(m_i).onStop();
                m_i++;

                if (m_i < m_tasks.size()) {
                    m_tasks.get(m_i).onStart(timestamp);
                }
            }
        }
    }

    public boolean isDone() {
        return m_i > m_tasks.size();
    }

    public void onStop() {

    }
}
