package com.grapper;

//Import fileexport
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

//Import htmlunit
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class App {
    public static void main(String[] args) {
        //Initialize the webclient using Chrome
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //Disable CSS
        webClient.getOptions().setCssEnabled(false); 

        //Disable Exception Messages
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false); 
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);

        //Disable Javascript 
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.waitForBackgroundJavaScript(3_000);


        try {
            //Access page using WebClient
            HtmlPage page = webClient.getPage("https://www.tokopedia.com/search?st=product&q=handphone");    
            
            //Get Title Text 
            String title = page.getTitleText();
            System.out.println("Page Title: " + title);

            //Declare FileWriter and Add first row file
            FileWriter recipesFile = new FileWriter("recipes.csv");
            recipesFile.write("Nama of Product,Product Link,Image Link,Price,Rating,Merchant\n");

            //Retrieve scrapping data from Product Container
            List<DomElement> items = page.getByXPath("//div[@class='pcv3__container css-k03rs0']");

            if(items.isEmpty()){ //If there are no product
                System.out.println("No product found");
            }else{ //If there are products
                //Retrieve specified data inside product container
                for(DomElement htmlItem: items){
                    //Get Product Link, Image Link, Item Name, and Product Price data from container
                    HtmlAnchor product_link = htmlItem.getFirstByXPath(".//a"); //Get Data of Product Link
                    HtmlImage image_link = htmlItem.getFirstByXPath(".//img"); //Get data of Image Link
                    HtmlDivision span_name = htmlItem.getFirstByXPath(".//div[@class='css-1f4mp12']"); //Get data of Item Name
                    HtmlDivision payment = htmlItem.getFirstByXPath(".//div[@class='css-rhd610']"); //Get data of product price
                  
                    //Storing data into string
                    String item_name = span_name.getTextContent(); //Store Item Name Data
                    String image_src = image_link.getAttribute("src"); //Store Image Link Data
                    String price = payment.getTextContent(); //Store Item Price data
                    String seller_link = product_link.getHrefAttribute(); //Store Product Link data

                    //Retrieve scrapping data from Inner Product Container
                    List<DomElement> item_merchant = htmlItem.getByXPath(".//div[@class='css-vogbyu']");
                   
                    String merchant = ""; //Create string variable to store seller name
                    String rating = ""; //Create string variable to store product rating
                    for(DomElement merchant_rating: item_merchant){

                        //Get Product Ratings and Seller Names data from inner container
                        HtmlSpan rats = merchant_rating.getFirstByXPath(".//span[@class='css-etd83i']"); //Get data of ratings
                        HtmlSpan store_name = merchant_rating.getFirstByXPath(".//span[@class='css-qjiozs flip'][last()]"); //Get data of Merchant Name

                        //Storing data into string
                        merchant = store_name.getTextContent(); //Store merchant name data
                        rating = rats.getTextContent(); //Store product rating data
                        
                        //Write all stored info into .csv file
                        recipesFile.write(item_name + "," + seller_link + "," +image_src + "," + price + "," + rating + "," + merchant + "\n");
                    }
                    
                }
            }
            
            recipesFile.close(); //Close csv file
            webClient.close(); //Close WebClient
        
         }catch (IOException e){
            System.out.println("An error occurred: " + e);
        }
        
        
    }
}
