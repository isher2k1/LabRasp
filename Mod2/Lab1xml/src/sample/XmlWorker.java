package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XmlWorker {
    private final Agency agency;

    public ArrayList<Category> getCategories() {
        return agency.getCategories();
    }

    XmlWorker() throws ParserConfigurationException, IOException, SAXException {
        agency = new Agency();
        File inputFile = new File("agency.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("category");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element authorElement = (Element) nNode;
                Category category = new Category(Integer.valueOf(authorElement.getAttribute("code")),
                        authorElement.getAttribute("name"));
                agency.addCategory(category);
                NodeList items = authorElement.getElementsByTagName("item");

                for (int j = 0; j < items.getLength(); j++) {
                    Node fNode = items.item(j);

                    if (fNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element bookElement = (Element) fNode;
                        Item item = new Item(Integer.valueOf(bookElement.getAttribute("id")),
                                bookElement.getAttribute("headline"), bookElement.getAttribute("author"));
                        category.addItem(item);
                    }
                }

            }
        }
    }

    public void saveToXml() {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("agency");
            document.appendChild(root);
            for (int i = 0; i < agency.countCategories(); i++) {
                Element categoryElement = document.createElement("category");
                categoryElement.setAttribute("code", String.valueOf(agency.getCategories().get(i).code));
                categoryElement.setAttribute("name", agency.getCategories().get(i).name);
                for (int j = 0; j < agency.getCategories().get(i).getItems().size(); j++) {
                    Item item = agency.getCategories().get(i).getItems().get(j);
                    Element itemElement = document.createElement("item");
                    itemElement.setAttribute("id", String.valueOf(item.id));
                    itemElement.setAttribute("headline", item.headline);
                    itemElement.setAttribute("author", item.author);
                    categoryElement.appendChild(itemElement);
                }
                root.appendChild(categoryElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("agency.xml"));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void addCategory(Category category) {
        agency.addCategory(category);
    }

    public void addItemByCategory(int code, Item item) {
        Category category = agency.getCategory(code);
        category.addItem(item);
    }

    public Category findCategoryByCode(int code) {
        return agency.getCategory(code);
    }

    public void updateCategory(int code, int newCode, String newName) {
        Category category = agency.getCategory(code);
        category.name = newName;
        category.code = newCode;
    }


    public void deleteCategory(int code) {
        Category category = agency.getCategory(code);
        getCategories().remove(category);
    }

    public void deleteItem(int codeCategory, int itemId){
        Category category = agency.getCategory(codeCategory);
        Item item = category.findItemById(itemId);
        category.getItems().remove(item);
    }

}
