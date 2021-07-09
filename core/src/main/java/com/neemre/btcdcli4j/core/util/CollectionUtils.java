package com.neemre.btcdcli4j.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.neemre.btcdcli4j.core.common.Errors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtils {
	
	public static List<Object> asList(Object... items) {
		List<Object> itemsList = new ArrayList<Object>(items.length);
		for (Object item : items) {
			if (item != null) {
				itemsList.add(item);
			}
		}
		return itemsList;
	}
	
	public static Map<Object, Object> asMap(Object... items) {
		if (!NumberUtils.isEven(items.length)) {
			throw new IllegalArgumentException(Errors.ARGS_COUNT_UNEVEN.getDescription());
		}
		Map<Object, Object> pairsMap = new HashMap<Object, Object>(items.length / 2);
		for (int i = 0; i < items.length; i = i + 2) {
			pairsMap.put(items[i], items[i + 1]);
		}
		return pairsMap;
	}

	@SafeVarargs
	public static List<Object> merge(List<? extends Object>... lists) {
		if (containsNull(lists)) {
			throw new IllegalArgumentException(Errors.ARGS_CONTAIN_NULL.getDescription());
		}
		List<Object> mergedList = new ArrayList<Object>();
		for (List<? extends Object> list : lists) {
			mergedList.addAll(list);
		}
		return mergedList;
	}

	@SafeVarargs
	public static List<Object> mergeInterlaced(List<? extends Object>... lists) {
		if (containsNull(lists)) {
			throw new IllegalArgumentException(Errors.ARGS_CONTAIN_NULL.getDescription());
		}
		if (!equalsSize(lists)) {
			throw new IllegalArgumentException(Errors.ARGS_COUNT_UNEQUAL.getDescription());
		}
		List<Object> mergedList = new ArrayList<Object>();
		if (lists.length > 0) {
			for (int i = 0; i < lists[0].size(); i++) {
				for (List<? extends Object> list : lists) {
					mergedList.add(list.get(i));
				}
			}
		}
		return mergedList;	
	}

	@SafeVarargs
	public static boolean equalsSize(List<? extends Object>... lists) {
		Set<Integer> sizes = new HashSet<Integer>(lists.length);
		for (List<? extends Object> list : lists) {
			if (list == null) {
				return false;
			}
			if (sizes.isEmpty()) {
				sizes.add(list.size());
			} else {
				if (sizes.add(list.size())) {
					return false;
				}
			}
		}
		return true;
	}

	public static Object[] asLists(Object[]... arrays) {
		if (containsNull(arrays)) {
			throw new IllegalArgumentException(Errors.ARGS_CONTAIN_NULL.getDescription());
		}
		Object[] lists = new Object[arrays.length];
		for (int i = 0; i < arrays.length; i++) {
			lists[i] = Arrays.asList(arrays[i]);
		}
		return lists;
	}

	public static boolean containsNull(Object[]... arrays) {
		for (Object[] array : arrays) {
			if (array == null) {
				return true;
			}
		}
		return false;
	}

	@SafeVarargs
	public static boolean containsNull(List<? extends Object>... lists) {
		for (List<? extends Object> list : lists) {
			if (list == null) {
				return true;
			}
		}
		return false;
	}
	
	public static <T> List<T> duplicate(T reference, int count) {
		List<T> references = new ArrayList<T>();
		for (int i = 0; i < count; i++) {
			references.add(reference);
		}
		return references;
	}
}