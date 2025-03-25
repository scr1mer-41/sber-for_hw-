import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class CompositionOfEvenTest extends TestCase{

    @Test
    public void testNumberNotEvenException() throws NumberNotEvenException{
        CompositionOfEven composition = new CompositionOfEven();
        double a = 2;
        double b = 1;
        try {
            double res = composition.composition(a,b);
            Assert.fail("Expected NumberNotEvenException");
        } catch (NumberNotEvenException e) {
            Assert.assertNotEquals("", e.getMessage());
        }
    }
}
