package ai.shortlyst.vendingmachine.exception;

/**
 *
 * @author Ahmad Mudzakkir Amal
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

  private final String message;

  public BusinessException( String message) {
    super();
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "BusinessException{" +
        "message='" + message + '\'' +
        "} " + super.toString();
  }
}
