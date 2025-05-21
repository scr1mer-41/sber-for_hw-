import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.classfile.FieldTransform;
import java.util.*;

public class Parser {
    public String[] productNames;
    public String mainURl = "http://frs24.ru";

    public Parser() {
        update();
//        for (String str : productNames) {
//            System.out.println(str);
//        }
    }



    public void update() {
        try {

            Document document = Jsoup.connect(mainURl + "/himsostav/").get();
            Elements page1 = document.select("div.page1");
            Elements elementsOfPage1 = page1.select("a");
            String[] productNames = new String[elementsOfPage1.size()];

            for (Element element : elementsOfPage1) {
                productNames[elementsOfPage1.indexOf(element)] = element.text().toLowerCase();
            }
            this.productNames = productNames;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> coincidences(String name) {
        String[] ArrayName = name.toLowerCase().split("\\s+");
        List<String> founded = new ArrayList<>();

        for (String str : this.productNames) {
            int cnt = 0;
            for (String partOfName : ArrayName) {
                if (str.contains(partOfName)) {
                    cnt += 1;
                }
            }
            if ((cnt == ArrayName.length) && (str.split("\\s+").length >= ArrayName.length)) {
                founded.add(str);
            }
        }
        return founded;
    }



    public String found(String name) {
        String result = "None";
        String[] ArrayName = name.toLowerCase().split("\\s+");
        List<String> founded = new ArrayList<>();

        for (String str : this.productNames) {
            int cnt = 0;
            for (String partOfName : ArrayName) {
                if (str.contains(partOfName)) {
                    cnt += 1;
                }
            }
            if ((cnt == ArrayName.length) && (str.split("\\s+").length == ArrayName.length)) {
                founded.add(str);
            }
        }

        if (founded.size() == 1) {
            result = founded.getFirst();
        } else if (founded.size() > 1) {
            result = "Many";
        }
        return result;
    }


    public int foundKal(String IdealName) {
        int result = 0;
        try {
            Document document = Jsoup.connect(mainURl + "/himsostav/").get();
            Elements page = document.select("div.page1");
            Elements list = page.select("a");

            String href = "";
            String name = IdealName.substring(0,1).toUpperCase() + IdealName.substring(1);
            for (Element element : list) {
                if (element.hasText() & element.text().equals(name)) {
                    href = element.attr("href");
                }
            }

            Document document1 = Jsoup.connect(mainURl + href).get();
            Element firstTable = document1.select("table.skl").first();
            Elements str = firstTable.select("tr");

            for (Element element : str) {
                Elements row = element.select("td");
                if (row.first().text().equals("Калорийность")) {
                    result = Integer.parseInt(row.get(1).text().split("\\s+")[0]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int getKal(String strWithWeight) {
        // - валидность
        int result = 0;
        String nameOfProduct = strWithWeight.split("\\s+")[0];
        String weight = strWithWeight.split("\\s+")[1];

        List<String> list = coincidences(nameOfProduct);

        String idealName = "";
        if (list.size() == 1) {
            idealName = list.getFirst();
        }

        Float value = foundKal(idealName) * 10 * Float.parseFloat(weight);
        if (!Objects.equals(idealName, "None")) {
            result = value.intValue();
        }
        return result;
    }

    public boolean validationOfNote(String strWithWeight) {
        String[] list = strWithWeight.split("\\s+");
        if (list.length != 2 ) {
            return false;
        }
        try {
            Float _weight_ = Float.parseFloat(list[1].toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
