package reflexion;

public class Main {
    public static void main(String[] args) {

        /* Создать аннотацию @Analyzable (может применяться к методам, конструкторам, свойствам класса).
         * Создать класс реализующий интерфейс ICodeAnalyzer. Метод analyze() должен проверять все
         * свойства, методы, конструкторы в классе clazz, и если они помечены аннотацией @Analyzable:
         * - вывести на консоль имя свойства/метода/конструктора, все аннотации которыми помечен этот элемент;
         * - если это метод/конструктор - то вывести на консоль имена и типы параметров метода. */

        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        codeAnalyzer.analyzeClass(CodeAnalyzer.class);
    }
}
