package de.cfknet.railway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class ResultTest {

	@Test
	void success() throws Exception {
		Result<String, Object> success = Result.success("Foo");
		success.onSuccess(t -> assertThat(t, is("Foo")))
				.onFailure(t -> fail("should not be executed"));
		assertThat(success.isSuccess(), is(true));
		assertThat(success.getS(), is("Foo"));
		assertThrows(NoSuchElementException.class, () -> success.getF());
	}

	@Test
	void success_but_no_value() throws Exception {
		assertThrows(NullPointerException.class, () -> Result.success(null));
	}

	@Test
	void failure() throws Exception {
		Result<Object, String> failure = Result.failure("Ups");
		failure.onSuccess(t -> fail("should not be executed"))
				.onFailure(t -> assertThat(t, is("Ups")));
		assertThat(failure.isSuccess(), is(false));
		assertThat(failure.getF(), is("Ups"));
		assertThrows(NoSuchElementException.class, () -> failure.getS());
	}

	@Test
	void failure_but_no_value() throws Exception {
		assertThrows(NullPointerException.class, () -> Result.failure(null));
	}

	@Test
	void map() throws Exception {
		Result<String, Object> success = Result.success("Foo");
		Result<Integer, Object> map = success.map(t -> {
			assertThat(t, is("Foo"));
			return 42;
		});

		assertThat(map.getS(), is(42));
	}

	@Test
	void map_if_failure() throws Exception {
		Result<Object, String> failure = Result.failure("Foo");
		Result<Object, String> map = failure.map(t -> {
			fail("Should not be called");
			return t + "bar";
		});

		assertThat(map.getF(), is("Foo"));
	}

	@Test
	void flatMap() throws Exception {
		Result<String, Object> success = Result.success("Foo");
		Result<Integer, Object> flatMap = success.flatMap(t -> {
			assertThat(t, is("Foo"));
			return Result.success(42);
		});

		assertThat(flatMap.getS(), is(42));
	}

	@Test
	void flatMap_if_failure() throws Exception {
		Result<Object, String> failure = Result.failure("Foo");
		Result<Object, String> map = failure.flatMap(t -> {
			fail("Should not be called");
			return null;
		});

		assertThat(map.getF(), is("Foo"));
	}

	@Test
	void orElseGet_on_success() throws Exception {
		Result<String, Integer> success = Result.success("Foo");
		String map = success.orElseGet(t -> {
			assertThat(t, is("Foo"));
			return t + "bar";
		});

		assertThat(map, is("Foo"));
	}

	@Test
	void orElseGet_on_failure() throws Exception {
		Result<String, Integer> failure = Result.failure(42);
		String map = failure.orElseGet(t -> {
			assertThat(t, is(42));
			return "Foobar";
		});

		assertThat(map, is("Foobar"));
	}
}
