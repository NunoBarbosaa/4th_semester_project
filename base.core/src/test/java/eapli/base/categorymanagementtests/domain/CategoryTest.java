package eapli.base.categorymanagementtests.domain;

import eapli.base.categorymanagement.domain.Category;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class CategoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void ensureAlphanumericCodeMustNotBeEmpty(){
        System.out.println("must have non-empty code");
        new Category("","categoria produtos");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAlphanumericCodeMustNotBeNull(){
        System.out.println("must have non-null code");
        new Category(null,"categoria produtos");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionMustNotBeEmpty(){
        System.out.println("must have non-empty description");
        new Category("123","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionMustNotBeNull(){
        System.out.println("must have non-null description");
        new Category("123",null);
    }

    @Test
    public void ensureCreatedCategoryIsActive(){
        final String alphanumericCode = "alpha01";
        final Category instance = new Category(alphanumericCode,"description of alpha 01");

        final boolean state = instance.isActive();
        instance.toogleState();

        assertEquals(!state, instance.isActive());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotChangeDescriptionToNull(){
        System.out.println("ChangeDescriptionTo -New description must not be null");
        final Category instance = new Category("alpha01","alpha description");
        instance.changeDescriptionTo(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCannotChangeDescriptionToEmpty() {
        System.out.println("ChangeDescriptionTo -New description must not be empty");
        final Category instance = new Category("alpha01", "alpha description");
        instance.changeDescriptionTo("");
    }

    @Test
    public void ensureCanChangeDescription() {
        System.out.println("test changeDescriptionTo");
        final Category instance = new Category("alpha01", "alpha descriptionuigiugiugui");
        final String newDescription = "new Descriptionnnhuihiuhiuoihoihoiioho";
        instance.changeDescriptionTo(newDescription);
        assertEquals(newDescription, instance.description());
    }
    @Test
    public void ensureCategoryCreatedWithAnIdentityHasThatIdentity(){
        System.out.println("Ensure id is created");

        final String alphanumericCode="alpha01";
        final Category instance = new Category("alpha01", "alpha descriptionhuhiuhuihihu");

        assertEquals(alphanumericCode,instance.identity());
    }

    @Test
    public void ensureCategoryCreatedWithAnIdentityHasThatIdentity2(){
        System.out.println("Has that identity");
        final String id = "alpha01";
        final String description = "alpha descriptionnnnnnnnnnnnnn";
        final Category instance = new Category(id, description);
        assertTrue(instance.hasIdentity(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCategoryDescriptionHasMoreThan20Chars(){
        System.out.println("Has that identity");
        final String id = "alpha01";
        final String description = "alpha";
        final Category instance = new Category(id, description);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCategoryCodeHasLessThan10Chars(){
        System.out.println("Has that identity");
        final String id = "alpha0132232323";
        final String description = "alpha";
        final Category instance = new Category(id, description);
    }

}
