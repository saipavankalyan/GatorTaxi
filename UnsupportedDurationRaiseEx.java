public class UnsupportedDurationRaiseEx extends Exception {

    // Custom Exception when updating the ride whose new duration is 2x greater than
    // old
    public UnsupportedDurationRaiseEx() {
        super("New Duration Raise is not supported as it is greater than twice the old duration");
    }

}
