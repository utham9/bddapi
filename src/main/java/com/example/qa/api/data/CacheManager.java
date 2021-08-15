package com.example.qa.api.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CacheManager {

  private static final Object monitor = new Object();
  private static CacheManager instance;
  private final Map<String, Object> cache =
      Collections.synchronizedMap(new HashMap<String, Object>());

  private CacheManager() {}

  public static CacheManager getInstance() {
    if (instance == null) {
      synchronized (monitor) {
        if (instance == null) {
          instance = new CacheManager();
        }
      }
    }
    return instance;
  }

  public void put(String cacheKey, Object value) {
    cache.put(cacheKey, value);
  }

  public Object get(String cacheKey) {
    return cache.get(cacheKey);
  }

  public boolean contains(String cacheKey) {
    return cache.containsKey(cacheKey);
  }

  public void clear(String cacheKey) {
    cache.put(cacheKey, null);
  }

  public void clear() {
    cache.clear();
  }
}
