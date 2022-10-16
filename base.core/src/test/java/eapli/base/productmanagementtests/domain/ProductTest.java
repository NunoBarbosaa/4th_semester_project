package eapli.base.productmanagementtests.domain;

import eapli.base.categorymanagement.domain.Category;
import eapli.base.productmanagement.domain.Barcode;
import eapli.base.productmanagement.domain.Product;
import eapli.base.utils.Description;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductTest {

    String internalCode = "12309f.sjfds";
    Barcode barcode = new Barcode("1234567891234");
    Description shortDescription = new Description("short");
    Description extendedDescription = new Description("Extended description with at least 20 chars");


    @Test(expected = IllegalArgumentException.class)
    public void ensurecInternalCodeMustNotBeEmpty(){
        System.out.println("must have non-empty code");
        new Product("",shortDescription,extendedDescription,barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureInternalCodeMustNotBeNull(){
        System.out.println("must have non-null code");
        new Product(null,shortDescription,extendedDescription,barcode);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureInternalCodeMustNotHaveMoreThan23Chars(){
        System.out.println("must have less than 23 chars");
        new Product("foasdjkpoifjkadsjdfisoajfijadoijfidjsfdsjfsdij",shortDescription,extendedDescription,barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureBarCodeMustNotBeNull(){
        System.out.println("must have non-null code");
        new Product(internalCode,shortDescription,extendedDescription,null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureBarCodeDataMustNotBeNull(){
        System.out.println("must have non-null code");
        new Product(internalCode,shortDescription,extendedDescription,new Barcode(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureShortDescriptionMustNotBeEmpty(){
        System.out.println("must have non-empty code");
        new Product(internalCode,new Description(""),extendedDescription,barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureShortDescriptionMustNotBeNull(){
        System.out.println("must have non-empty code");
        new Product(internalCode,null,extendedDescription,barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureExtendedDescriptionMustNotHaveMoreThan30Chars(){
        System.out.println("must not have more than 30 chars");
        new Product(internalCode,new Description("afsdkjfoasdjoifjsajifjdioajoijfdosijfijdsiojfiodsjfiojdsifjdsijfkdsjfs"),extendedDescription,barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureExtendedDescriptionMustNotBeEmpty(){
        System.out.println("must have non-empty code");
        new Product(internalCode,shortDescription,new Description(""),barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureExtendedDescriptionMustNotBeNull(){
        System.out.println("must have non-empty code");
        new Product(internalCode,shortDescription,null,barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureShortDescriptionMustNotHaveLessThan20Chars(){
        System.out.println("must not have more than 30 chars");
        new Product(internalCode,shortDescription,new Description("392dks"),barcode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureShortDescriptionMustNotHaveMoreThan100Chars(){
        System.out.println("must not have more than 30 chars");
        new Product(internalCode,shortDescription,new Description("392dksksidjfoijsadofjidaoifdjsoijoidsjfohjp8weouihjaouhfdsojhfoiasdjoifjuaoisjdfiojwoiejfoaisdjfoijoiwjfewijdijsklfjdsoijfjwiejkjfdsiofjwioejfkdsjfiowejfksdjfiowejfks"),barcode);
    }

    @Test
    public void ensureCreatedProductIsActive(){
        final Product instance = new Product(internalCode,shortDescription,extendedDescription,barcode);
        final boolean state = instance.isActive();
        instance.setActive(false);

        assertEquals(!state, instance.isActive());
    }

    @Test
    public void ensureCreatedProductsAreTheSameWithTheSameInternalCode(){
        final Product instance = new Product(internalCode,shortDescription,extendedDescription,barcode);
        final Product instance2 = new Product(internalCode,new Description("different"),extendedDescription,new Barcode("barcode891234"));

        assertTrue(instance.sameAs(instance2));
    }
    @Test
    public void ensureCreatedProductsWithIdentityHasThatIdentity(){
        final String identity = "identity";
        final Product instance = new Product(identity,shortDescription,extendedDescription,barcode);

        assertEquals(identity,instance.identity());
    }


}
