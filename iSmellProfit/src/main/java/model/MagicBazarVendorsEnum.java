package model;

import java.util.stream.Stream;

public enum MagicBazarVendorsEnum {

    MAZVIGOSL("Mazvigosl");



    private final String value;

    MagicBazarVendorsEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value; //will return , or ' instead of COMMA or APOSTROPHE
    }

    public String getValue() {
        return value;
    }

    public static Stream<MagicBazarVendorsEnum> stream() {
        return Stream.of(MagicBazarVendorsEnum.values());
    }
}
