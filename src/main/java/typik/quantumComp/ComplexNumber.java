package typik.quantumComp;

import java.math.BigDecimal;
import java.math.MathContext;

public class ComplexNumber {
	
	public static final ComplexNumber ZERO = new ComplexNumber( 0 , 0 );
	
	
	private BigDecimal real;
	private BigDecimal imaginary;

	public ComplexNumber(Number realPart, Number imaginaryPart) {
		this.real      = new BigDecimal( realPart.toString      () );
		this.imaginary = new BigDecimal( imaginaryPart.toString () );
	}

	public ComplexNumber getConjugate() {
		return new ComplexNumber( this.real, this.imaginary.negate() );
	}
	
	@Override
	public boolean equals( Object obj ){
		if ( obj != null && obj instanceof ComplexNumber ){
			ComplexNumber cm = (ComplexNumber)obj;
			return cm.getReal().equals( getReal() ) && cm.getImaginary().equals( getImaginary() );
		}
		return false;
	}

	public BigDecimal getReal() {
		return real;
	}

	public BigDecimal getImaginary() {
		return imaginary;
	}

	public ComplexNumber multiply(ComplexNumber complexNumber) {
		return new ComplexNumber( 
				this.real.multiply( complexNumber.real ).subtract(
						this.imaginary.multiply( complexNumber.imaginary ) ),
				this.imaginary.multiply( complexNumber.real ).add( 
						this.real.multiply( complexNumber.imaginary ) ) );
	}

	public ComplexNumber add(ComplexNumber complexNumber ) {
		return new ComplexNumber( 
				this.real.add( complexNumber.real ), 
				this.imaginary.add( complexNumber.imaginary ) );
	}

	public Matrix multiply(Matrix matrixAdamar) {
		ComplexNumber[][] c = new ComplexNumber[ matrixAdamar.getRowsCount() ][ matrixAdamar.getColumnsCount() ];
		for( int i = 0; i < matrixAdamar.getRowsCount() ; ++i ){
			for( int j = 0; j < matrixAdamar.getColumnsCount() ; ++j ){
				c[i][j] = this.multiply( matrixAdamar.get( i + 1 , j + 1 ) );
			}
		}
		return new Matrix( c );
	}

	public boolean equals(ComplexNumber complexNumber, MathContext mathContext) {
		if ( mathContext == null ){
			return this.equals( complexNumber );
		}
		return this.real.round( mathContext ).compareTo( complexNumber.real.round( mathContext ) ) == 0 &&
				this.imaginary.round( mathContext ).compareTo( complexNumber.imaginary.round( mathContext ) ) == 0;
	}


}
