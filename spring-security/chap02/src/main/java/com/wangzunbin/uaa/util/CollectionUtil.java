package com.wangzunbin.uaa.util;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CollectionUtil {
    public static TreeSet<? extends GrantedAuthority> toTreeSet(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().collect(
            Collectors.toCollection(
                () -> new TreeSet<>(
                    Comparator.comparing(GrantedAuthority::getAuthority)
                )
            )
        );
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);
        }
        return list;
    }
}
