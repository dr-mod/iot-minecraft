package org.drmod.representation.display;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.drmod.domain.Led;
import org.drmod.representation.Notificator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RPiPentaLedView extends AbstractView {

    private final GpioController gpio;
    private final GpioPinDigitalOutput pinGreen;
    private final GpioPinDigitalOutput pinYellow;
    private final GpioPinDigitalOutput pinRed;
    private final GpioPinDigitalOutput pinSideGreen;
    private final GpioPinDigitalOutput pinSideRed;

    private final Map<Byte, GpioPinDigitalOutput> mapper;

    public RPiPentaLedView(Notificator notificator, GpioController gpio) {
        super(notificator);
        this.gpio = gpio;
        pinGreen = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
        pinYellow = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
        pinRed = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);
        pinSideGreen = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
        pinSideRed = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);

        Map<Byte, GpioPinDigitalOutput> tempMapper = new HashMap<>();
        tempMapper.put((byte) 1, pinGreen);
        tempMapper.put((byte) 2, pinYellow);
        tempMapper.put((byte) 3, pinRed);
        tempMapper.put((byte) 4, pinSideGreen);
        tempMapper.put((byte) 5, pinSideRed);
        mapper = Collections.unmodifiableMap(tempMapper);
    }


    @Override
    public void update(final Led status) {
        GpioPinDigitalOutput pinToUpdate = mapper.get(status.getNumber());
        if (null != pinToUpdate) {
            setState(pinToUpdate, status.isHigh());
        }
    }

    private void setState(GpioPinDigitalOutput pin, boolean state) {
        if (state) {
            pin.high();
        } else {
            pin.low();
        }
    }

}
