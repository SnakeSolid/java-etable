package ru.snake.xored.etable;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CellReferenceTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException1() {
		new CellReference(-1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException2() {
		new CellReference(0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException3() {
		new CellReference(26, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException4() {
		new CellReference(0, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException5() {
		new CellReference("A0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException6() {
		new CellReference("Z0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException7() {
		new CellReference("a1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowException8() {
		new CellReference("z1");
	}

	@Test()
	public void shouldCreateReference1() {
		CellReference reference = new CellReference(0, 0);

		assertReference(reference, 0, 0);
	}

	@Test()
	public void shouldCreateReference2() {
		CellReference reference = new CellReference("A1");

		assertReference(reference, 0, 0);
	}

	@Test()
	public void shouldCreateReference3() {
		CellReference reference = new CellReference("B2");

		assertReference(reference, 1, 1);
	}

	@Test()
	public void shouldCreateReference4() {
		CellReference reference = new CellReference("Z9");

		assertReference(reference, 25, 8);
	}

	/**
	 * Check that reference has expected row and column indexes
	 * 
	 * @param reference
	 * @param column
	 * @param row
	 */
	private void assertReference(CellReference reference, int column, int row) {
		assertThat(reference.getColumn(), equalTo(column));
		assertThat(reference.getRow(), equalTo(row));
	}

}
