package reversi;

public interface Observer {
	void Notify(Subject whoFrom);
	boolean isvalid(Subject whoFrom);
}
