import org.jdom2.*;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.*;
import org.jdom2.output.support.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.*;
import java.io.*;

public class JDomTest {
    public static void main(String[] args) throws Exception {
        Document w3cDoc = createW3CDocument();
        org.jdom2.Document jdomDoc = new DOMBuilder().build(w3cDoc);

        XMLOutputter outputter = new XMLOutputter();
        Format format = Format.getPrettyFormat();
        format.setOmitEncoding(false);
        outputter.setFormat(format);
        outputter.setXMLOutputProcessor(new MyXMLOutputProcessor());
        outputter.output(jdomDoc, System.out);
    }

    private static class MyXMLOutputProcessor extends AbstractXMLOutputProcessor {
        @Override
        protected void printAttribute(Writer out, FormatStack fstack, Attribute attribute) throws IOException {
            if (!attribute.isSpecified() && fstack.isSpecifiedAttributesOnly()) {
                return;
            }
            fstack.push();

            write(out, fstack.getPadBetween());
            write(out, attribute.getQualifiedName());
            write(out, "=");

            write(out, "\"");
            attributeEscapedEntitiesFilter(out, fstack, attribute.getValue());
            write(out, "\"");

            fstack.pop();
        }

        @Override
        protected void printNamespace(Writer out, FormatStack fstack, Namespace ns) throws IOException {
            fstack.push();
            write(out, fstack.getPadBetween());
            super.printNamespace(out, fstack, ns);
            fstack.pop();
        }

        @Override
        protected void write(Writer out, String str) throws IOException {
            if (" />".equals(str) || " xmlns".equals(str)) {
                str = str.trim();
            }
            super.write(out, str);
        }
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
