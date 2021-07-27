package Assignment_Questionnaire.Library;

import Assignment_Questionnaire.Student;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergiy Isakov
 * <p>
 * Reflection utility class
 */
@SuppressWarnings("all")
public class RU {

    /**
     * Typical reflection magic to get value from variable
     *
     * @param field
     * @return
     */
    public static Object getValueByName(String field, Object o) {
        Object value = null;
        try {
            value = (o.getClass().getField(field).get(o));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            value = null;
        }
        return value;
    }

    /**
     * Same magic to get a method from anonymous object, knowing its name.
     *
     * @param methodName
     * @param object
     * @return
     */
    public static Object getMethod(String methodName, Object object) {
        Class clazz = object.getClass();
        if (object instanceof Class) {
            clazz = (Class) object;
        }
        Object result = null;
        try {
            Method method = clazz.getMethod(methodName);
            result = method.invoke(object);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Following three methods are mainly for supervised learning method as ID3:
     * countAttributeCategories(), listAttributeCategories() and listAttributeCategories()
     * and one helping method innerFunction()
     */

    /**
     * Method which might be helpful for calculating information gain. Counts the number of
     * a specific Class label that appears in given data, when a specific Attribute has a specific value.
     * The method assumes that the attribute is nominal.
     *
     * @param data
     * @param Attribute      Attribute we are calculating information gain for
     * @param AttributeValue The specific value of the attribute
     * @param mainClass      Main Class Label
     * @param Classification of main Class Label
     * @return number of items for given Class Label containing specific Attribute
     */
    public static int countAttributeCategories(Collection<Student> data, Object Attribute, Object AttributeValue, Object mainClass, Object Classification) {
        return (int) data.stream().filter(student -> {
            if (student.getAttributeValue(mainClass).equals(Classification)) {
                return innerFunction(student, Attribute, AttributeValue);
            }
            return false;
        }).count();
    }

    /**
     * Same method as countAttributeCategories() but returns a list of Items
     *
     * @param data
     * @param Attribute      Attribute we are calculating information gain for
     * @param AttributeValue The specific value of the attribute
     * @param mainClass      Main Class Label
     * @param Classification of main Class Label
     * @return
     */
    public static List<Student> listAttributeCategories(Collection<Student> data, Object Attribute, Object AttributeValue, Object mainClass, Object Classification) {
        return data.stream().filter(student -> {
            if (student.getAttributeValue(mainClass).equals(Classification)) {
                return innerFunction(student, Attribute, AttributeValue);
            }
            return false;
        }).collect(Collectors.toList());
    }

    /**
     * Method similar to listAttributeCategories() but it returns Items only containing a specific AttributeValue for a given
     * Attribute Class. For example can be used to calculate a number of students that are on SDT_DT course.
     *
     * @param data
     * @param Attribute      Attribute we are calculating information gain for
     * @param AttributeValue The specific value of the attribute
     * @return
     */
    public static List<Student> listAttributeCategories(Collection<Student> data, Object Attribute, Object AttributeValue) {
        return data.stream().filter(student -> innerFunction(student, Attribute, AttributeValue)).collect(Collectors.toList());
    }

    /**
     * Helping and common method for methods  countAttributeCategories(), listAttributeCategories() and listAttributeCategories().
     * It considers if attribute of contains multiple values i.e. an array. Complicated case, it makes some error in calculations
     * for Info of attribute in ID3 algorithm, especially if array is long. But it had to be worked around somehow.
     *
     * @param student        an Item of a student
     * @param Attribute      Attribute we are calculating information gain for
     * @param AttributeValue The specific value of the attribute
     * @return
     */
    private static boolean innerFunction(Object student, Object Attribute, Object AttributeValue) {
        Object object = ((Student) student).getAttributeValue(Attribute);
        if (object.getClass().isArray())
            for (int i = 0; i < ((Object[]) object).length; i++) {
                if (((Object[]) object)[i].equals(AttributeValue))
                    return true;
            }
        if (object.equals(AttributeValue)) return true;
        return false;
    }
}
