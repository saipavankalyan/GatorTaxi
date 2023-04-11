public class UnsupportedDurationRaiseEx extends Exception {

    public UnsupportedDurationRaiseEx() {
        super("New Duration Raise is not supported as it is greater than twice the old duration");
    }

}
