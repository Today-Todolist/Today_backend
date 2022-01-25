package todolist.today.today.global.security.path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum ApiPath {
    ;

    private final String path;
    private final HttpMethod method;
    private final boolean usePath;
    private final boolean useParam;
    private final boolean certify;

    public static List<String> getCertifyPath(HttpMethod method) {
        return Arrays.stream(ApiPath.values())
                .filter(apiPath -> apiPath.method.equals(method))
                .filter(apiPath -> apiPath.certify)
                .map(apiPath -> {
                    String path = apiPath.path;
                    if(apiPath.usePath) path = path.replaceAll("(\\{)\\w+(})", "*");
                    if(apiPath.useParam && !path.endsWith("*")) path += "*";
                    return path;
                }).toList();
    }

}
