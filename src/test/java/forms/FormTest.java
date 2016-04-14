package forms;

import static org.junit.Assert.*;
import org.junit.Test;

import forms.Form;

public class FormTest {

    @Test
    public void formHoldsStringData() {
        String data = "some string";
        Form form = new Form(data);
        assertEquals(form.getData(), data);
    }

    @Test
    public void formHoldsStringCollectionData() {
        String[] data = new String[] {"some string", "some other string", "this is data"};
        Form form = new Form(data);
        assertTrue(form.getDataCollection().equals(data));
    }
}
