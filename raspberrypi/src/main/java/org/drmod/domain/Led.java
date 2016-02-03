package org.drmod.domain;

public class Led {

    private byte number;
    private boolean status;

    public Led(final byte[] data) {
        this.number = data[0];
        this.status = data[1] == 0b00000001;

    }

    public boolean isHigh() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public byte getNumber() {

        return number;
    }

    public void setNumber(final byte number) {
        this.number = number;
    }
}
