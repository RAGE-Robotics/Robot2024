package com.ragerobotics.robot2024.systems;

import java.util.Optional;

import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Robot.Mode;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LEDs implements ISystem {
    private static LEDs instance;

    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;

    private int m_rainbowFirstPixelHue;

    public static boolean greenOn;

    private Optional<Alliance> alliance;

    private LEDs() {
        m_led = new AddressableLED(Constants.kLEDChannel);
        m_ledBuffer = new AddressableLEDBuffer(600);
        m_led.setLength(m_ledBuffer.getLength());
        m_led.setData(m_ledBuffer);
        m_led.start();
        alliance = DriverStation.getAlliance();
    }

    public static synchronized LEDs getInstance() {
        if (instance == null) {
            instance = new LEDs();
        }

        return instance;
    }

    public void allianceColor() {

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            for (var i = 0; i < m_ledBuffer.getLength(); i++) {
                m_ledBuffer.setRGB(i, 255, 0, 0);
            }
        }

        else {
            for (var i = 0; i < m_ledBuffer.getLength(); i++) {
                m_ledBuffer.setRGB(i, 0, 0, 255);
            }
        }

        m_led.setData(m_ledBuffer);

        greenOn = false;

    }

    public void rainbowColors() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;

            m_ledBuffer.setHSV(i, hue, 255, 128);
        }

        m_rainbowFirstPixelHue += 3;

        m_rainbowFirstPixelHue %= 180;

        m_led.setData(m_ledBuffer);

    }

    public void whiteLights() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            m_ledBuffer.setRGB(i, 255, 255, 255);
        }
    }

    public void noteIn() {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            m_ledBuffer.setRGB(i, 92, 203, 131);
        }

        greenOn = true;
    }

    @Override
    public void onUpdate(double timestamp, Mode mode) {

    }
}
