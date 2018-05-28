package restaurant.service.bill;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import restaurant.model.Dish;
import restaurant.model.Orderr;

import java.io.IOException;

@Service
public class BillServiceImpl implements BillService {
    @Override
    public void generateBill(Orderr orderr) {
        String fileName = "Bill_"+orderr.getClientName()+"_"+orderr.getId()+".pdf";
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
            content.beginText();
            content.setFont(PDType1Font.COURIER, 15);
            content.setLeading(20f);
            content.newLineAtOffset(20, 700);
            content.showText("Your receit");
            content.newLine();
            content.newLine();
            for (Dish dish : orderr.getDishes().keySet()) {
                int quantity=orderr.getDishes().get(dish);
                content.showText(dish.getName()+"   x "+quantity+"    "+dish.getPrice()*quantity);
                content.newLine();
            }
            content.showText("Total:  "+orderr.getReceit());
            content.newLine();
            if(orderr.getRating()!=0){
                content.showText("Thank you for giving us "+orderr.getRating()+" stars!");
                content.newLine();
            }
            content.showText("Your server: "+orderr.getEmployees().getName());
            content.newLine();
            content.endText();
            content.close();
            doc.save(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
