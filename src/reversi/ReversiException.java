package reversi;

/**
 * Customize Exception for Reversi, same as normal exception
 */

public class ReversiException extends Exception {
    public enum Flags{
        PIECE_ALREADY_EXIST("Piece already exist"),
        PIECE_NOT_VAILD("Not a valid place");
        public final String message;
        Flags(final String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }

    Flags flags;

    public ReversiException(Flags flag){
        this.flags = flag;
    }

    @Override
    public String getMessage() {
        return flags.getMessage();
    }
}
