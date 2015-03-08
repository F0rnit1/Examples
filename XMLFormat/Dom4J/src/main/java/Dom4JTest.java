import org.dom4j.*;
import org.dom4j.io.*;

import java.io.*;
import java.lang.reflect.Field;

public class Dom4JTest {
    public static void main(String[] args) throws Exception {
        Document document = DocumentFactory.getInstance().createDocument();
        Element root = document.addElement("root", "http://uri.com");
        root.addAttribute("att1", "val1");
        root.addAttribute("att2", "val2");
        Element child = root.addElement("child");
        child.addAttribute("a", "Ку-Ку");
        child.addAttribute("b", "Привет");
        child.addAttribute("c", "\"\"");

        OutputFormat prettyPrint = OutputFormat.createPrettyPrint();
        prettyPrint.setNewLineAfterDeclaration(false);
        XMLWriter writer = new XMLWriter(new PrintWriter(System.out), prettyPrint) {
            protected Integer getIndentLevel() {
                try {
                    Field field = XMLWriter.class.getDeclaredField("indentLevel");
                    field.setAccessible(true);
                    return (Integer) field.get(this);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            protected void writeAttributes(Element element) throws IOException {
                Integer indentLevel = getIndentLevel();
                setIndentLevel(indentLevel + 1);

                for (int i = 0, size = element.attributeCount(); i < size; i++) {
                    Attribute attribute = element.attribute(i);
                    println();
                    indent();

                    char quote = getOutputFormat().getAttributeQuoteCharacter();
                    writer.write(attribute.getQualifiedName());
                    writer.write("=");
                    writer.write(quote);
                    writeEscapeAttributeEntities(attribute.getValue());
                    writer.write(quote);
                }
                setIndentLevel(indentLevel);
            }

            @Override
            protected void writeNamespace(String prefix, String uri) throws IOException {
                Integer indentLevel = getIndentLevel();
                setIndentLevel(indentLevel + 1);

                println();
                indent();

                if ((prefix != null) && (prefix.length() > 0)) {
                    writer.write("xmlns:");
                    writer.write(prefix);
                    writer.write("=\"");
                } else {
                    writer.write("xmlns=\"");
                }

                writer.write(uri);
                writer.write("\"");

                setIndentLevel(indentLevel);
            }
        };
        writer.write(document);
        writer.close();
    }
}
