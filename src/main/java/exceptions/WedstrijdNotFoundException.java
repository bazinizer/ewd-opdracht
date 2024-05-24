package exceptions;

public class WedstrijdNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WedstrijdNotFoundException(Long id) {
        super("Could not find wedstrijd with id " + id);
    }
}
