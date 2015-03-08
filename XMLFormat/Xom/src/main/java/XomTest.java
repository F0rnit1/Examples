import nu.xom.*;
import nu.xom.converters.DOMConverter;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.*;
import java.io.*;

public class XomTest {
    public static void main(String[] args) throws Exception {
        nu.xom.Document doc = DOMConverter.convert(createW3CDocument());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Serializer serializer = new Serializer(baos, "UTF-8") {
            @Override
            protected void write(Attribute attribute) throws IOException {
                breakLine();
                String repeat = StringUtils.repeat("  ", getIndent() + 1);
                writeRaw(repeat);
                write(new Text(repeat));
                super.write(attribute);
            }
        };
        serializer.setIndent(2);
        serializer.write(doc);

        baos.writeTo(System.out);
    }

    private static Document createW3CDocument() throws ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document w3cDoc = documentBuilder.newDocument();

        Element root = w3cDoc.createElementNS("http://uri.com", "root");
        w3cDoc.appendChild(root);
        root.setAttribute("abc", "xxx");
        root.setAttribute("bbb", "yyy-");
        root.setAttribute("ccc", "zzz\"");
        Element child = w3cDoc.createElement("child");
        root.appendChild(child);
        child.setAttribute("a", "Привет ВАСЯ");
        child.setAttribute("b", "2");
        child.setAttribute("c", "3");

        return w3cDoc;
    }
}
