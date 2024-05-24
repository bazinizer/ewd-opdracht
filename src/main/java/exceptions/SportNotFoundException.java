package exceptions;

public class SportNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SportNotFoundException(Long id) {
        super("Could not find sport with id " + id);
    }
}
