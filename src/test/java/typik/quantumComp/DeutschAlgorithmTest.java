package typik.quantumComp;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.Test;

public class DeutschAlgorithmTest {
	
	@Test
	public void test(){		
		Function<BigDecimal,BigDecimal> f1 = el -> el;
		Function<BigDecimal,BigDecimal> f2 = el -> BigDecimal.ONE;
		
		assertFalse ( DeutschAlgorithm.isFunctionConstant( f1 ) );
		assertTrue  ( DeutschAlgorithm.isFunctionConstant( f2 ) );
	}

}
