package aitrader.util.observable;

@FunctionalInterface
public interface Handler<T>
{
	void handle(T t);
}

