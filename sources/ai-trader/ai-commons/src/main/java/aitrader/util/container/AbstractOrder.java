package aitrader.util.container;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractOrder {
  protected Map<String, Object> parameters = new HashMap<>();

  public void setParameter(String name, Object value) {
    parameters.put(name, value);
  }

  public boolean hasParameter(String name) {
    return parameters.containsKey(name);
  }

  public Object getParameter(String name) {
    return hasParameter(name) ? parameters.get(name) : null;
  }

  public void absorbParameters(AbstractOrder order) {
    parameters.putAll(order.parameters);
  }
}
