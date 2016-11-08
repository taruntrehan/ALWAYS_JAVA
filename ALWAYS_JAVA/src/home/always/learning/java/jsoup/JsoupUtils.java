package home.always.learning.java.jsoup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class JsoupUtils {
	
	public static void main(String[] args) {
		fetchTopPostsUrl();
	}
	
	public static List<String> fetchTopPostsUrl()
    {

        List<String> topPostsUrl = new ArrayList<String>();

        try {


            Document doc = Jsoup.connect("http://clone.allzhere.in/").get();

            Elements asideElements = doc.select("aside");
            //System.out.println(asideElements);
            for (Iterator iterator = asideElements.iterator(); iterator.hasNext();) {
                Element element = (Element) iterator.next();
                if(element.hasAttr("id") && element.attr("id").equals("dsgnwrks_google_top_posts_widgets-3"))
                {
                    //System.out.println("Found matching element");
                    List<Node> asideNodes = element.childNodes();
                    for (Node node : asideNodes) {
                        //System.out.println("Node Val"+node);
                        //System.out.println(node.nodeName());
                        if(node.nodeName().equals("ol"))
                        {
                            //System.out.println("OL Present");
                            List<Node> nodeChildren = node.childNodes();
                            //System.out.println("Node Children:"+nodeChildren);
                            for (Iterator ulIter = nodeChildren.iterator(); ulIter.hasNext();) {
                                //System.out.println("loop");
                                Node urlNode = (Node) ulIter.next();
                                //System.out.println(urlNode.attributes());
                                List<Node> liNodes = urlNode.childNodes();
                                for (Iterator liIter = liNodes.iterator(); liIter
                                        .hasNext();) {
                                    Node hrefNode = (Node) liIter.next();
                                    //System.out.println(hrefNode);
                                    //System.out.println(hrefNode.attr("href"));
                                    topPostsUrl.add(hrefNode.attr("href"));
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println("Gen Utils"+topPostsUrl);
        return topPostsUrl;
    }
}
