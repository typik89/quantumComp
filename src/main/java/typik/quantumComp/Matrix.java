package typik.quantumComp;

import java.math.MathContext;

public class Matrix {
	
	public static Matrix I = new Matrix( new ComplexNumber[][]{ 
		{ new ComplexNumber( 1 , 0 ) , new ComplexNumber( 0 , 0 ) } , 
		{ new ComplexNumber( 0 , 0 ) , new ComplexNumber( 1 , 0 ) } } );
	public static Matrix H = new Matrix( new ComplexNumber[][]{ 
		{ new ComplexNumber( 1 , 0 ) , new ComplexNumber( 0 , 0 ) } , 
		{ new ComplexNumber( 0 , 0 ) , new ComplexNumber( 1 , 0 ) } } ).multiply( new ComplexNumber( 1 / Math.sqrt( 2 ) , 0 ) );
	public static Matrix X = new Matrix( new ComplexNumber[][]{ 
		{ new ComplexNumber( 0 , 0 ) , new ComplexNumber( 1 , 0 ) } , 
		{ new ComplexNumber( 1 , 0 ) , new ComplexNumber( 0 , 0 ) } } );
	public static Matrix Y = new Matrix( new ComplexNumber[][]{ 
		{ new ComplexNumber( 0 , 0 ) , new ComplexNumber( 0 , -1 ) } , 
		{ new ComplexNumber( 0 , 1 ) , new ComplexNumber( 0 , 0  ) } } );
	public static Matrix Z = new Matrix( new ComplexNumber[][]{ 
		{ new ComplexNumber( 1 , 0 ) , new ComplexNumber( 0  , 0 ) } , 
		{ new ComplexNumber( 0 , 0 ) , new ComplexNumber( -1 , 0 ) } } );
	
	private ComplexNumber[][] data;

	
	
	public Matrix(ComplexNumber[][] data) {
		this.data = data;
	}

	private Matrix multiply(ComplexNumber complexNumber) {
		ComplexNumber[][] newData = new ComplexNumber[getColumnsCount()][getRowsCount()];
		for( int i = 0 ;i < getColumnsCount(); ++i ){
			for( int j = 0; j < getRowsCount(); ++j ){
				newData[i][j] = data[i][j].multiply( complexNumber );
			}
		}
		return new Matrix( newData );
	}

	public ComplexNumber get(int rowIndex,int columnIndex) {
		return data[rowIndex-1][columnIndex-1];
	}

	public Matrix getHermetianMatrix() {
		ComplexNumber[][] newData = new ComplexNumber[ getRowsCount() ][ getColumnsCount() ];
		for( int i = 0; i < getRowsCount(); ++i ){
			for( int j = 0; j < getColumnsCount() ; ++j ){
				newData[i][j] = data[j][i].getConjugate();
			}
		}
		return new Matrix( newData );
	}

	public int getRowsCount() {
		return data.length;
	}

	public int getColumnsCount(){
		return data[0].length;
	}

	
	
	public Matrix multiply(Matrix m2) {
		if ( getColumnsCount() != m2.getRowsCount() ){
			throw new IllegalArgumentException();
		}		
		ComplexNumber[][] c = new ComplexNumber[ getRowsCount() ][ m2.getColumnsCount() ];
		for( int i = 0; i < getRowsCount() ; ++i ){
			for( int j = 0; j < m2.getColumnsCount() ; ++j ){
				c[i][j] = new ComplexNumber( 0 , 0 );
				for( int k = 0 ; k < m2.getRowsCount() ; ++k ){
					c[i][j] = c[i][j].add( 
							get( i + 1 , k + 1 ).multiply( m2.get( k + 1 , j + 1 ) ) );
				}
			}
		}
		return new Matrix( c );
	}

	public boolean isUnitary( MathContext mathContext ) {
		if ( getRowsCount() != getColumnsCount() ){
			return false;
		}		
		Matrix resMatrix = this.multiply( getHermetianMatrix() );
		for( int i = 0; i < getRowsCount(); ++i ){
			for( int j = 0; j < getColumnsCount(); ++j ){
				if(  i == j && !resMatrix.get( i + 1, j + 1 ).equals( new ComplexNumber( 1 , 0 ) , mathContext )){
					return false;
				}
				if(  i != j && !resMatrix.get( i + 1, j + 1 ).equals( ComplexNumber.ZERO , mathContext )){
					return false;
				}			
			}
		}
		return true;
	}

	public Matrix tensorMultiply(Matrix m2) {
		ComplexNumber[][] data = new ComplexNumber[ getRowsCount()*m2.getRowsCount() ][ getColumnsCount()*m2.getColumnsCount() ];
		for( int i = 0; i < getRowsCount() * m2.getRowsCount(); ++i ){
			for( int j = 0; j < getColumnsCount() * m2.getColumnsCount(); ++j ){
				int i1 = i / m2.getRowsCount    ();
				int j1 = j / m2.getColumnsCount ();
				int i2 = i % m2.getRowsCount    ();
				int j2 = i % m2.getColumnsCount ();
				data[i][j] = this.data[i1][j1].multiply(  m2.data[i2][j2] ); 
			}
		}
		return new Matrix( data );
	}
	
	public Matrix tensorPower( int pow ){
		if ( pow < 1 ){
			throw new IllegalArgumentException( "Power can't be less than 1" );
		}
		Matrix result = this;
		for( int i = 0; i < pow - 1; ++i ){
			result = result.tensorMultiply( this );
		}
		return result;
	}
	
	
}
