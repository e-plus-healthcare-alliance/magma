package org.obiba.magma.type;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.obiba.magma.MagmaDate;
import org.obiba.magma.MagmaEngine;
import org.obiba.magma.Value;

public class DateType extends AbstractValueType {

  private static final long serialVersionUID = -149385659514790222L;

  private static WeakReference<DateType> instance;

  private final SimpleDateFormat ISO_8601 = new SimpleDateFormat("yyyy-MM-dd");

  private DateType() {

  }

  public static DateType get() {
    if(instance == null || instance.get() == null) {
      instance = MagmaEngine.get().registerInstance(new DateType());
    }
    return instance.get();
  }

  @Override
  public boolean isDateTime() {
    return true;
  }

  @Override
  public boolean isNumeric() {
    return false;
  }

  @Override
  public Class<?> getJavaClass() {
    return MagmaDate.class;
  }

  @Override
  public String getName() {
    return "date";
  }

  @Override
  public boolean acceptsJavaClass(Class<?> clazz) {
    return MagmaDate.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || java.sql.Date.class.isAssignableFrom(clazz) || java.sql.Timestamp.class.isAssignableFrom(clazz) || Calendar.class.isAssignableFrom(clazz);
  }

  @Override
  public String toString(Object object) {
    if(object != null) {
      return ((MagmaDate) object).toString();
    }
    return null;
  }

  @Override
  public Value valueOf(String string) {
    if(string == null) {
      return nullValue();
    }

    try {
      // DateFormat is not thread safe
      synchronized(ISO_8601) {
        return Factory.newValue(this, new MagmaDate(ISO_8601.parse(string)));
      }
    } catch(ParseException e) {
      throw new IllegalArgumentException("Cannot parse date from string value '" + string + "'. Expected format is " + ISO_8601.toPattern());
    }
  }

  @Override
  public Value valueOf(Object object) {
    if(object == null) {
      return nullValue();
    }

    Class<?> type = object.getClass();
    if(type.equals(MagmaDate.class)) {
      return Factory.newValue(this, (MagmaDate) object);
    } else if(type.equals(Date.class)) {
      return Factory.newValue(this, new MagmaDate((Date) object));
    } else if(Date.class.isAssignableFrom(type)) {
      return Factory.newValue(this, new MagmaDate(new Date(((Date) object).getTime())));
    } else if(Calendar.class.isAssignableFrom(type)) {
      return Factory.newValue(this, new MagmaDate((Calendar) object));
    }
    throw new IllegalArgumentException("Cannot construct " + getClass().getSimpleName() + " from type " + object.getClass() + ".");
  }

  @Override
  public int compare(Value o1, Value o2) {
    return ((MagmaDate) o1.getValue()).compareTo((MagmaDate) o2.getValue());
  }
}
