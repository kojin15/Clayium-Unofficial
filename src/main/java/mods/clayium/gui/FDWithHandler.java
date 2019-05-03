package mods.clayium.gui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class FDWithHandler<S, T>
  implements IFunctionalDrawer<T>
{
  protected IFunctionalIterable<IFDHandler<S, T>, T> defaultHandlers = null;
  
  public FDWithHandler(IFunctionalIterable<IFDHandler<S, T>, T> defaultHandlers)
  {
    this.defaultHandlers = defaultHandlers;
  }
  
  public FDWithHandler(List<IFDHandler<S, T>> defaultHandlers)
  {
    this(new FunctionalList(defaultHandlers));
  }
  
  public FDWithHandler(IFDHandler<S, T> defaultHandler)
  {
    this(Arrays.asList((IFDHandler[])new IFDHandler[] { defaultHandler }));
  }
  
  public FDWithHandler()
  {
    this((IFDHandler)null);
  }
  
  public T draw(T param)
  {
    return (T)render(applyHandler(param));
  }
  
  public abstract T render(T paramT);
  
  public T applyHandler(T param)
  {
    IFunctionalIterable<IFDHandler<S, T>, T> iterable = getHandlerIterator(param);
    if (iterable == null) {
      return param;
    }
    IFunctionalIterator<IFDHandler<S, T>, T> iterator = iterable.iterator(param);
    if (iterator == null) {
      return param;
    }
    while (iterator.hasNext(param))
    {
      IFDHandler<S, T> handler = (IFDHandler)iterator.next(param);
      param = handler.apply(preApplyHandler(param), param);
    }
    return param;
  }
  
  public abstract S preApplyHandler(T paramT);
  
  public IFunctionalIterable<IFDHandler<S, T>, T> getHandlerIterator(T param)
  {
    return this.defaultHandlers;
  }
  
  public static abstract interface IFDHandler<S, T>
  {
    public abstract T apply(S paramS, T paramT);
  }
  
  public static abstract interface IFunctionalIterable<S, T>
  {
    public abstract IFunctionalIterator<S, T> iterator(T paramT);
  }
  
  public static abstract interface IFunctionalIterator<S, T>
  {
    public abstract boolean hasNext(T paramT);
    
    public abstract S next(T paramT);
  }
  
  public static class FunctionalList<S, T>
    implements IFunctionalIterable<S, T>
  {
    Iterable<S> iterable;
    
    public FunctionalList(Iterable<S> iterable)
    {
      this.iterable = iterable;
    }
    
    public IFunctionalIterator<S, T> iterator(T param)
    {
      return new FunctionalIterator(this.iterable.iterator());
    }
  }
  
  public static class FunctionalIterator<S, T>
    implements IFunctionalIterator<S, T>
  {
    Iterator<S> iterator;
    
    public FunctionalIterator(Iterator<S> iterator)
    {
      this.iterator = iterator;
    }
    
    public boolean hasNext(T param)
    {
      return this.iterator.hasNext();
    }
    
    public S next(T param)
    {
      return (S)this.iterator.next();
    }
  }
}
