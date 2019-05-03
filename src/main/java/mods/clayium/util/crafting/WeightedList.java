package mods.clayium.util.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedList<E>
  extends ArrayList<WeightedResult<E>>
{
  public boolean add(E result, int weight)
  {
    return add(new WeightedResult(result, weight));
  }
  
  public List<E> getResultList()
  {
    List list = new ArrayList();
    for (int i = 0; i < size(); i++) {
      list.add(((WeightedResult)get(i)).result);
    }
    return list;
  }
  
  public int getWeightSum()
  {
    int sum = 0;
    for (int i = 0; i < size(); i++) {
      sum += ((WeightedResult)get(i)).weight;
    }
    return sum;
  }
  
  public E put(Random random)
  {
    int sum = getWeightSum();
    if (sum <= 0) {
      return (E)((WeightedResult)get(random.nextInt(size()))).result;
    }
    int pos = random.nextInt(sum);
    sum = 0;
    for (int i = 0; i < size(); i++)
    {
      sum += ((WeightedResult)get(i)).weight;
      if (sum > pos) {
        return (E)((WeightedResult)get(i)).result;
      }
    }
    return null;
  }
}
