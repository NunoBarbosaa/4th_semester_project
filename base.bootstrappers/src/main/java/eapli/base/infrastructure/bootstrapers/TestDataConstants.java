/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.infrastructure.bootstrapers;

import java.util.Calendar;

import eapli.base.categorymanagement.domain.Category;
import eapli.base.customermanagement.dto.AddressDTO;
import eapli.base.productmanagement.domain.Barcode;
import eapli.base.productmanagement.domain.Brand;
import eapli.base.productmanagement.domain.Price;
import eapli.base.productmanagement.domain.Reference;
import eapli.base.utils.Description;
import eapli.base.warehouse.domain.AgvStatus;
import eapli.framework.time.util.Calendars;

public final class TestDataConstants {
    //Category
    public static final String ALPHACODE1 = "AF892H";
    public static final String DESCRIPTION1="Produtos Manutenção e Limpeza";

    public static final String ALPHACODE2 = "CIO32H";
    public static final String DESCRIPTION2="Produtos de Lazer e Diversão";
    //AGV
    public static final String AGVID="AGV1";
    public static final String AGV_DESCRICAO = "AGV1 Warehouse 1";
    public static final String AGV_MODEL="AGV MDL-1";
    public static final double AGV_MAX_WEIGHT= 10;
    public static final double AGV_MAX_VOLUME= 0.8;
    public static final double AGV_AUTONOMY= 2;
    public static final AgvStatus AGV_STATUS= AgvStatus.AVAILABLE;
    //PRODUCT
    public static final String INT_CODE = "VGC0001";
    public static final String INT2_CODE ="VGC0002";
    public static final Barcode BARCODE = new Barcode("8984576587483");
    public static final Barcode BARCODE2=new Barcode("8984576587485");
    public static final Description EXT_DESC=new Description("Xbox Series X 1TB Matte Black");
    public static final Description EXT_DEC2=new Description("PlayStation 5 500GB Digital Edition");
    public static final Description SHORT_DESC=new Description("Xbox Series X");
    public static final Description SHORT_DESC2=new Description("PS5 Digital Edition");
    public static final Brand PRODUCT_BRAND =new Brand("Microsoft");
    public static final Brand PRODUCT_BRAND2=new Brand("Sony");
    public static final Price PRODUCT_PRICE = new Price(749.99,799.99);
    public static final Price PRODUCT_PRICE2=new Price(599.99,649.99);
    public static final Category PRODUCT_CATEGORY =new Category("VGC", "Video Games & Consoles");

    //CUSTOMER
    public static final String FIRST_NAME="Carlos";
    public static final String LAST_NAME="Santos";
    public static final String VAT="PT123456789";
    public static final String DIALCODE="+351";
    public static final String EMAIL="roberto@gmail.com";
    public static final long PHONENUMBER =913123456;
    public static final int BIRTHDAY=10;
    public static final int MONTHBIRTHDAY=2;
    public static final int YEARBIRTHDAY=2002;
    public static final String gender="MALE";
    public static final AddressDTO addressDTO = new AddressDTO("4550-128", "Rua da Cedofeita", 2, "Paiva", "Portugal", "Billing Address");


    public static final String USER_TEST1 = "user1";

    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD1 = "Password1";

    @SuppressWarnings("squid:S2885")
    public static final Calendar DATE_TO_BOOK = Calendars.of(2017, 12, 01);

    private TestDataConstants() {
        // ensure utility
    }
}
