package typik.quantumComp;

public class Vector extends Matrix {


	public Vector(ComplexNumber[] data) {
		super( new ComplexNumber[data.length][1] );
		for( int i = 0; i < data.length; ++i ){
			super.getData()[i] = new ComplexNumber[]{ data[i] };
		}
	}

}
