package de.cfknet.railway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class ResultTest {

	@Test
	void onSuccess_consumer() throws Exception {
		Result<String, Object> success = Result.success("Foo");
		success.onSuccess(t -> assertThat(t, is("Foo")))
				.onFailure(t -> fail("should not be executed"));
		assertThat(success.isSuccess(), is(true));
		assertThat(success.getS(), is("Foo"));
		assertThrows(NoSuchElementException.class, () -> success.getF());
	}

	@Test
	void onSuccess_supplier() throws Exception {
		Result<String, Object> success = Result.success("Foo");
		Result<String, Object> onSuccess = success.onSuccess(() -> "FooBar")
				.onFailure(f -> fail("should not be executed"));

		assertThat(onSuccess.isSuccess(), is(true));
		assertThat(onSuccess.getS(), is("FooBar"));
		assertThrows(NoSuchElementException.class, () -> onSuccess.getF());
	}

	@Test
	void onSuccess_supplier_but_is_failure() throws Exception {
		Result<String, Object> success = Result.failure("Foo");
		Result<String, Object> onSuccess = success.onSuccess(() -> "FooBar")
				.onFailure(f -> assertThat(f, is("Foo")));

		assertThat(onSuccess.isSuccess(), is(false));
		assertThat(onSuccess.getF(), is("Foo"));
		assertThrows(NoSuchElementException.class, () -> onSuccess.getS());
	}

	@Test
	void onFailure_supplier() throws Exception {
		Result<String, String> failure = Result.failure("Foo");
		Result<String, String> onSuccess = failure.onFailure(() -> "FooBar");

		assertThat(onSuccess.isSuccess(), is(false));
		assertThat(onSuccess.getF(), is("FooBar"));
		assertThrows(NoSuchElementException.class, () -> onSuccess.getS());
	}

	@Test
	void onFailure_supplier_but_is_succes() throws Exception {
		Result<String, String> failure = Result.success("Foo");
		Result<String, String> onFailure = failure.onFailure(() -> "FooBar")
				.onSuccess(s -> assertThat(s, is("Foo")));

		assertThat(onFailure.isSuccess(), is(true));
		assertThat(onFailure.getS(), is("Foo"));
		assertThrows(NoSuchElementException.class, () -> onFailure.getF());
	}

	@Test
	void success_but_no_value() throws Exception {
		assertThrows(NullPointerException.class, () -> Result.success(null));
	}

	@Test
	void onFailure_consumer() throws Exception {
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
