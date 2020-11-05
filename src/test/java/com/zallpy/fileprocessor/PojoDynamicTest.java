package com.zallpy.fileprocessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import com.zallpy.fileprocessor.dto.Client;
import com.zallpy.fileprocessor.dto.Report;
import com.zallpy.fileprocessor.dto.Sale;
import com.zallpy.fileprocessor.dto.SaleItem;
import com.zallpy.fileprocessor.dto.Salesman;

class PojoDynamicTest {

	@Test
	void testFX() {
		dynamicAccess(
			Client.class,
			Sale.class,
			Salesman.class,
			SaleItem.class,
			Report.class
		);
	}

	private void dynamicAccess(final Class<?> ... clazzz) {
		for (final Class<?> clazz : clazzz) {
			final Object t1 = getInstance(clazz);
			final Object t2 = getInstance(clazz);
			final Object t3 = getInstance(clazz);
			final List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
			methods.sort((m1, m2) -> m2.getName().compareTo(m1.getName()));
			for (final Method method : methods) {
				try {
					final Object[] argNull = { null };
					final String name = method.getName();
					if (name.startsWith("get")
							|| name.equals("toString")
							|| name.equals("hashCode")) {
						method.invoke(t1);
						method.invoke(t3);
					} else if (name.equals("equals")
							|| name.equals("canEqual")) {
						final Object t4 = getInstance(clazz);
						final Object t5 = getInstance(clazz);
						final Object t6 = null;
						BeanUtils.copyProperties(t1, t4);
						method.invoke(t1, ""); //Other class
						method.invoke(t1, t1); //Same object
						method.invoke(t1, t2); //Different object and different field
						method.invoke(t1, t4); //Different object and same field
						method.invoke(t1, t3); //Different object, field1 not null and field3 null
						method.invoke(t3, t4); //Different object, field3 null and field4 not null
						method.invoke(t3, t5); //Different object, field3 null and field5 null
						method.invoke(t1, t6); //Different object, null object
					} else if (name.startsWith("set")) {
						final Parameter[] parameters = method.getParameters();
						final Object[] args1 = new Object[parameters.length];
						final Object[] args2 = new Object[parameters.length];
						for (int i = 0; i < parameters.length; i++) {
							final Class<?> pClass = parameters[i].getType();
							if (name.equals("setId")) {
								args1[i] = 1L;
								args2[i] = 2L;
							} else {
								args1[i] = getValorDefault(pClass);
								args2[i] = getValorDefault(pClass);
							}
						}
						method.invoke(t1, args1);
						method.invoke(t2, args2);
						method.invoke(t3, argNull);
					} else if (name.startsWith("convertToEntity")) {
						final Parameter[] parameters = method.getParameters();
						final Class<?> pClass = parameters[0].getType();
						final Object[] args = new Object[parameters.length];
						final Class<?> rClass = method.getReturnType();
						if (rClass.isEnum()) {
							final Object objEnum = getValues(rClass)[0];
							for (final Method methodEnum : objEnum.getClass().getDeclaredMethods()) {
								final Class<?> returnType = methodEnum.getReturnType();
								if (pClass.equals(returnType)) {
									args[0] = methodEnum.invoke(objEnum);
								}
							}
							method.invoke(t1, args);
						}
						method.invoke(t1, argNull);
						try {
							if (Character.class.isAssignableFrom(pClass)) {
									method.invoke(t1, Character.valueOf('Z'));
							} else if (String.class.isAssignableFrom(pClass)) {
								method.invoke(t1, "Z");

							}
						} catch (final Exception e) {
						}
					} else if (name.startsWith("convertToDatabase")) {
						final Parameter[] parameters = method.getParameters();
						final Object[] args = new Object[parameters.length];
						final Class<?> pClass = parameters[0].getType();
						if (pClass.isEnum()) {
							args[0] = getValues(pClass)[0];
						}
						method.invoke(t1, args);
					}
				} catch (final Exception e) {
				}
			}
			final Constructor<?>[] cons = clazz.getDeclaredConstructors();
			for (final Constructor<?> con : cons) {
				final Parameter[] parameters = con.getParameters();
				final Object[] args = new Object[parameters.length];
				for (int i = 0; i < parameters.length; i++) {
					try {
						final Class<?> pClass = parameters[i].getType();
						args[i] = getValorDefault(pClass);
						con.newInstance(args);
					} catch (final Exception e) {
					}
				}
			}
		}
	}

	private Object getInstance(final Class<?> clazz) {
		return !clazz.isEnum() ? BeanUtils.instantiateClass(clazz) : getValues(clazz)[0];
	}

	private Object getValorDefault(final Class<?> pClass)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (pClass.isEnum()) {
			return getValues(pClass)[0];
		} else if (LocalDate.class.isAssignableFrom(pClass)) {
			return LocalDate.now();
		} else if (LocalDateTime.class.isAssignableFrom(pClass)) {
			return LocalDateTime.now();
		} else if (Character.class.isAssignableFrom(pClass)) {
			return Character.valueOf('A');
		} else if (Integer.class.isAssignableFrom(pClass)) {
			return 1;
		} else if (Long.class.isAssignableFrom(pClass)) {
			return 1L;
		} else if (BigDecimal.class.isAssignableFrom(pClass)) {
			return BigDecimal.ONE;
		} else {
			return BeanUtils.instantiateClass(pClass);
		}
	}

	private Object[] getValues(final Class<?> pClass) {
		try {
			final Method valuesMethod = pClass.getDeclaredMethod("values");
			final Object[] values = (Object[]) valuesMethod.invoke(null);
			return values;
		} catch (final Exception e) {
			return null;
		}
	}

}
