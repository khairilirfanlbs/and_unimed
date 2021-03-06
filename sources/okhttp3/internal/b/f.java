package okhttp3.internal.b;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import okhttp3.internal.c;

class f implements InvocationHandler {
    boolean a;
    String b;
    private final List<String> c;

    f(List<String> list) {
        this.c = list;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        String name = method.getName();
        Class returnType = method.getReturnType();
        if (objArr == null) {
            objArr = c.b;
        }
        if (name.equals("supports") && Boolean.TYPE == returnType) {
            return Boolean.valueOf(true);
        }
        if (name.equals("unsupported") && Void.TYPE == returnType) {
            this.a = true;
            return null;
        } else if (name.equals("protocols") && objArr.length == 0) {
            return this.c;
        } else {
            if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1 && (objArr[0] instanceof List)) {
                List list = (List) objArr[0];
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (this.c.contains(list.get(i))) {
                        name = (String) list.get(i);
                        this.b = name;
                        return name;
                    }
                }
                name = (String) this.c.get(0);
                this.b = name;
                return name;
            } else if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
                return method.invoke(this, objArr);
            } else {
                this.b = (String) objArr[0];
                return null;
            }
        }
    }
}
