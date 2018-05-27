package typik.quantumComp;

import java.math.MathContext;

public class Matrix {
	
	private ComplexNumber[][] data;

	public Matrix(ComplexNumber[][] data) {
		this.data = data;
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
	
	
}
