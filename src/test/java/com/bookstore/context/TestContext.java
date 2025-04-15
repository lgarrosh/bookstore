package com.bookstore.context;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TestContext {
	private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(context.get(key));
    }
}
