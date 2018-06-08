package typik.quantumComp;

import static org.junit.Assert.*;

import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;

public class MatrixTest {

	@Test
	public void testHermitianMatrix(){
		Matrix m1 = new Matrix(
				new ComplexNumber[][]{
					{ new ComplexNumber( 2 , 3  ) , new ComplexNumber( 3 , -1 ) },
					{ new ComplexNumber( 4 , 10 ) , new ComplexNumber( -1 , 2 ) }
				} );
		
		Matrix m2 = m1.getHermetianMatrix();
		
		assertEquals( new ComplexNumber( 2  , -3  ) , m2.get( 1 , 1 ) );
		assertEquals( new ComplexNumber( 4  , -10 ) , m2.get( 1 , 2 ) );
		assertEquals( new ComplexNumber( 3  , 1   ) , m2.get( 2 , 1 ) );
		assertEquals( new ComplexNumber( -1 , -2  ) , m2.get( 2 , 2 ) );
	}
	
	@Test
	public void testMultiply(){
		Matrix m1 = new Matrix(
				new ComplexNumber[][]{
					{ new ComplexNumber( 1 , 0  ) , new ComplexNumber( 2 , 0 ), new ComplexNumber( 5 , 0 ) },
					{ new ComplexNumber( 3 , 0  ) , new ComplexNumber( 4 , 0 ), new ComplexNumber( 0 , 0 ) }					
				});
		Matrix m2 = new Matrix(
				new ComplexNumber[][]{
					{ new ComplexNumber( 0 , 0  ) },
					{ new ComplexNumber( 2 , 0 )  }, 
					{ new ComplexNumber( -1 , 0 ) }					
				});
		Matrix result = m1.multiply( m2 );
		
		assertEquals( 2 , result.getRowsCount    () );
		assertEquals( 1 , result.getColumnsCount () );

		assertEquals( new ComplexNumber( -1 , 0 ) , result.get( 1 , 1 ) );
		assertEquals( new ComplexNumber( 8  , 0 ) , result.get( 2 , 1 ) );		
	}
	
	@Test
	public void testCheckUnitarity(){
		Matrix matrixAdamar = new Matrix( 
				new ComplexNumber[][]{
					{ new ComplexNumber( 1 , 0  ) , new ComplexNumber( 1  , 0 ) },
					{ new ComplexNumber( 1 , 0  ) , new ComplexNumber( -1 , 0 ) } } );
		matrixAdamar = new ComplexNumber( 1/ Math.sqrt( 2 ) , 0 ).multiply( matrixAdamar );
		assertTrue( matrixAdamar.isUnitary( new MathContext( 5 , RoundingMode.HALF_UP ) ) );
	}
	
	@Test
	public void testTensorMultiply(){
		Matrix m1 = new Matrix(
				new ComplexNumber[][]{
					{ new ComplexNumber( 1 , 0  ) , new ComplexNumber( 2 , 0 ), new ComplexNumber( 5 , 0 ) },
					{ new ComplexNumber( 3 , 0  ) , new ComplexNumber( 4 , 0 ), new ComplexNumber( 0 , 0 ) }					
				});
		Matrix m2 = new Matrix(
				new ComplexNumber[][]{
					{ new ComplexNumber( 0 , 0  ) },
					{ new ComplexNumber( 2 , 0 )  }, 
					{ new ComplexNumber( -1 , 0 ) }					
				});
		Matrix result = m1.tensorMultiply( m2 );

		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 1 , 1 ) );
		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 1 , 2 ) );
		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 1 , 3 ) );
		
		assertEquals( new ComplexNumber( 2  , 0 ), result.get( 2 , 1 ) );
		assertEquals( new ComplexNumber( 4  , 0 ), result.get( 2 , 2 ) );
		assertEquals( new ComplexNumber( 10 , 0 ), result.get( 2 , 3 ) );

		assertEquals( new ComplexNumber( -1 , 0 ), result.get( 3 , 1 ) );
		assertEquals( new ComplexNumber( -2 , 0 ), result.get( 3 , 2 ) );
		assertEquals( new ComplexNumber( -5 , 0 ), result.get( 3 , 3 ) );

		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 4 , 1 ) );
		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 4 , 2 ) );
		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 4 , 3 ) );

		assertEquals( new ComplexNumber( 6  , 0 ), result.get( 5 , 1 ) );
		assertEquals( new ComplexNumber( 8  , 0 ), result.get( 5 , 2 ) );
		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 5 , 3 ) );

		assertEquals( new ComplexNumber( -3 , 0 ), result.get( 6 , 1 ) );
		assertEquals( new ComplexNumber( -4 , 0 ), result.get( 6 , 2 ) );
		assertEquals( new ComplexNumber( 0  , 0 ), result.get( 6 , 3 ) );
	}
}
