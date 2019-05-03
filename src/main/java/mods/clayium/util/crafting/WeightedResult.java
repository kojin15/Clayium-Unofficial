package mods.clayium.util.crafting;

public class WeightedResult<E>
{
  public E result;
  public int weight;
  
  public WeightedResult(E result, int weight)
  {
    this.result = result;
    this.weight = weight;
    if (this.weight < 0) {
      this.weight = 0;
    }
  }
}
