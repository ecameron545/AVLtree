package impl;
/**
 * Thrown if local data stored in a node such as it's height or 
 * black height is inaccurate.
 */
public class IgnorantNodeException extends RuntimeException {
    private static final long serialVersionUID = 1226584659784286504L;
    public IgnorantNodeException(String msg) { super(msg); }
}
