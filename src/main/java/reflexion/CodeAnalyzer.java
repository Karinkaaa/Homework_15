package reflexion;

import com.sun.xml.internal.ws.developer.Serialization;

import java.beans.ConstructorProperties;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class CodeAnalyzer implements ICodeAnalyzer {

    @Serialization
    @Analyzable
    private String name;

    @ConstructorProperties(value = "constructor")
    public CodeAnalyzer() {
        name = "analyzer";
    }

    public CodeAnalyzer(String var) {
        name = var;
    }

    @Analyzable
    public String getName() {
        return name;
    }

    @Override
    // метод, анализирующий любой класс
    public void analyzeClass(Class<?> clazz) {

        this.showAvailableAnnotations(clazz.getDeclaredFields(), "Field", clazz);
        this.showAvailableAnnotations(clazz.getDeclaredMethods(), "Method", clazz);
        this.showAvailableAnnotations(clazz.getDeclaredConstructors(), "Constructor", clazz);
    }

    // показать доступные аннотации всех объектов
    private void showAvailableAnnotations(AccessibleObject[] accessibleObjects, String nameObj, Class<?> clazz) {

        System.out.println(String.format("%sS:\n", nameObj.toUpperCase()));

        for (AccessibleObject accessibleObj : accessibleObjects) {
            Annotation[] annotations = accessibleObj.getDeclaredAnnotations();

            if (annotations.length > 0) {
                System.out.println(String.format("%s %s(%s) is annotated:", nameObj,
                        this.getNameOfObject(accessibleObj), this.getParametersOfObject(accessibleObj)));
                this.showAnnotations(annotations);
            } else {
                System.out.println(String.format("%s %s(%s) isn't annotated.\n", nameObj,
                        this.getNameOfObject(accessibleObj), this.getParametersOfObject(accessibleObj)));
            }
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
    }

    // проверка, принадлежит ли объект к классу Method
    private boolean isClassMethod(Object obj) {
        return obj instanceof Method;
    }

    // проверка, принадлежит ли объект к классу Constructor
    private boolean isClassConstructor(Object obj) {
        return obj instanceof Constructor;
    }

    // позволяет получить конкретное имя объекта (без указания пути к данному объекту)
    private String getNameOfObject(Object obj) {

        if (this.isClassMethod(obj)) {
            return ((Method) obj).getName();
        } else if (this.isClassConstructor(obj)) {
            return this.getSubstring(((Constructor) obj).getName());
        }
        return ((Field) obj).getName();
    }

    // вывод всех аннотаций
    private void showAnnotations(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            System.out.println(this.getSubstring(annotation.toString()));
        }
        System.out.println();
    }

    // получение всех параметров указанного объекта
    private String getParametersOfObject(Object obj) {

        if (this.isClassConstructor(obj) || this.isClassMethod(obj)) {

            StringBuffer buffer = new StringBuffer();
            Parameter[] parameters = ((Executable) obj).getParameters();
            int size = parameters.length;

            for (int i = 0; i < size; i++) {

                buffer.append(getSubstring(parameters[i].getType().getName())).append(" ");
                buffer.append(getSubstring(parameters[i].getName()));

                if (i < size - 1)
                    buffer.append(", ");
            }
            return buffer.toString();
        }
        return "";
    }

    // получение подстроки
    String getSubstring(String str) {

        String[] strings = str.split("\\.");
        return strings[strings.length - 1];
    }
}
