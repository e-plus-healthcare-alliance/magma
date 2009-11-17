package org.obiba.magma.type;

import java.lang.ref.WeakReference;
import java.math.BigInteger;

import org.obiba.magma.MagmaEngine;
import org.obiba.magma.Value;

public class IntegerType extends AbstractNumberType {

  private static final long serialVersionUID = 2345566305016760446L;

  private static WeakReference<IntegerType> instance;

  private IntegerType() {

  }

  public static IntegerType get() {
    if(instance == null || instance.get() == null) {
      instance = MagmaEngine.get().registerInstance(new IntegerType());
    }
    return instance.get();
  }

  @Override
  public Class<?> getJavaClass() {
    return Long.class;
  }

  @Override
  public String getName() {
    return "integer";
  }

  @Override
  public boolean acceptsJavaClass(Class<?> clazz) {
    return Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz) || BigInteger.class.isAssignableFrom(clazz);
  }

  @Override
  public Value valueOf(String string) {
    return Factory.newValue(this, Long.valueOf(string));
  }

  @Override
  public Value valueOf(Object object) {
    if(object == null) {
      return Factory.newValue(this, null);
    }
    Class<?> type = object.getClass();
    if(Number.class.isAssignableFrom(type)) {
      return Factory.newValue(this, Long.valueOf(((Number) object).longValue()));
    } else if(type.isPrimitive()) {
      throw new UnsupportedOperationException();
    }
    throw new IllegalArgumentException("Cannot construct " + getClass().getSimpleName() + " from type " + object.getClass() + ".");
  }

}
