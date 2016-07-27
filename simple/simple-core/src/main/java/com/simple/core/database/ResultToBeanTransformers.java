package com.simple.core.database;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

/**
 * 澧炲己hibernate澶勭悊鍘熺敓sql杩斿洖缁撴灉鐨勮兘鍔�,鑷姩鏍规嵁鏁版嵁搴撳垪鍚嶈浆鎹㈠埌瀵硅薄鐨凚ean瀵硅薄涓�
 * 
 * 娉ㄦ剰锛� 鏁版嵁搴撶殑瀛楁闇�瑕佸拰bean瀵硅薄灞炴�у悕涓�鑷存垨鑰呮槸椹煎嘲鏍煎紡
 * 
 * @author hfren
 * 
 */
public class ResultToBeanTransformers implements ResultTransformer, Serializable {

	private static final long serialVersionUID = -1533163399071591329L;

	private final Class resultClass;
	private boolean isInitialized;
	private String[] aliases;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;

	public ResultToBeanTransformers(Class resultClass) {
		if (resultClass == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		}
		isInitialized = false;
		this.resultClass = resultClass;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;

		try {
			if (!isInitialized) {
				initialize(aliases);
			}
			else {
				check(aliases);
			}
			result = resultClass.newInstance();

			for (int i = 0; i < aliases.length; i++) {
				
				if (setters[i] != null) {
					if (tuple[i] instanceof BigInteger){
						long tempValue = Long.parseLong(tuple[i].toString());
						setters[i].set(result, tempValue, null);
					}else{
						setters[i].set(result, tuple[i], null);
					}
				}
			}
		}
		catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}
		catch (IllegalAccessException e) {
			throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
		}

		return result;
	}

	private void initialize(String[] aliases) {
		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] {
				PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
				PropertyAccessorFactory.getPropertyAccessor("field") });
		this.aliases = new String[aliases.length];
		setters = new Setter[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
			String alias = aliases[i];
			if (alias != null) {
				this.aliases[i] = alias;
				setters[i] = getSetterByColumnName(alias);
			}
		}
		isInitialized = true;
	}

	private Setter getSetterByColumnName(String alias) {
		// get POJO all property's name
		Field[] selfFields = resultClass.getDeclaredFields();
		Class pclass = resultClass.getSuperclass();
		Field[] superfields = pclass.getDeclaredFields();
		Field[] fields = new Field[selfFields.length + superfields.length];
		System.arraycopy(selfFields, 0, fields, 0, selfFields.length);
		System.arraycopy(superfields, 0, fields, selfFields.length, superfields.length);
		
		if (fields == null || fields.length == 0) {
			throw new RuntimeException("entity" + resultClass.getName() + "have not any property");
		}
		String proName = alias.replaceAll("_", "").toLowerCase();
		for (Field field : fields) {
			if (field.getName().toLowerCase().equals(proName)) {
				return propertyAccessor.getSetter(resultClass, field.getName());
			}
		}
		return null;
	}

	private void check(String[] aliases) {
		if (!Arrays.equals(aliases, this.aliases)) {
			throw new IllegalStateException("aliases are different from what is cached; aliases="
					+ Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
		}
	}

	public List transformList(List collection) {
		return collection;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ResultToBeanTransformers that = (ResultToBeanTransformers) o;

		if (!resultClass.equals(that.resultClass)) {
			return false;
		}
		if (!Arrays.equals(aliases, that.aliases)) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = resultClass.hashCode();
		result = 31 * result + (aliases != null ? Arrays.hashCode(aliases) : 0);
		return result;
	}

}
