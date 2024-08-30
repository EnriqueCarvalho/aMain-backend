package br.com.amain.backend.model;



import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;




public class ObjectHolder {
    private static final String IP_KEY = "IP";

    /**
     * Mapa para guardar objetos na thread.
     */
    private static final InheritableThreadLocal<Map<String, Object>> currentMap = new InheritableThreadLocal<>();

    private static Map<String, Object> getCurrentMap() {
        Map<String, Object> map = currentMap.get();
        if (map == null) {
            map = new LinkedHashMap<>(5);
            currentMap.set(map);
        }
        return map;
    }

    public static <T> Optional<T> getOptionalObject(final String key) {
        if (key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(getCurrentObject(key));
    }

    public static <T> Optional<T> getOptionalObject(final Class<T> clazz) {
        if (clazz == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(getCurrentObject(clazz));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCurrentObject(final String key) {
        if (key == null) {
            return null;
        }
        return (T) getCurrentMap().get(key);
    }

    public static <T> T getCurrentObject(final Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return getCurrentObject(clazz.getName());
    }

    public static void setCurrentObject(final Object object) {
        if (object != null) {
            setCurrentObject(object.getClass().getName(), object);
        }
    }

    public static void setCurrentObject(final String key, final Object object) {
        if (key == null) {
        } else if (object == null) {
            removeCurrentObject(key);
        } else {
            getCurrentMap().put(key, object);
        }
    }

    public static void removeCurrentObject(final Class<?> clazz) {
        if (clazz != null) {
            removeCurrentObject(clazz.getName());
        }
    }

    public static void removeCurrentObject(final String key) {
        if (key != null) {
            getCurrentMap().remove(key);
        }
    }

    public static void clearCurrentMap() {
        getCurrentMap().clear();
        currentMap.remove();
    }

    public static Usuario getCurrentUser() {
        return getCurrentObject(Usuario.class);
    }

    public static Optional<Usuario> getOptionalCurrentUser() {
        return getOptionalObject(Usuario.class);
    }

    public static Long getCurrentUserId() {
        return getOptionalCurrentUser().map(Usuario::getIdUsuario).orElse(null);
    }

    public static void setCurrentUser(Usuario usuario) {
        setCurrentObject(usuario);
    }

    public static void removeCurrentUser() {
        removeCurrentObject(Usuario.class);
    }

    public static String getCurrentIp() {
        return getCurrentObject(IP_KEY);
    }

    public static void setCurrentIp(String ip) {
        setCurrentObject(IP_KEY, ip);
    }

    public static void removeCurrentIp() {
        removeCurrentObject(IP_KEY);
    }

}
