package com.twigcodes.apidocs.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
public class OpenApiAggregationController {
    @GetMapping("/api-docs")
    public Mono<String> aggregateApis() {
        WebClient webClient = WebClient.create();

        List<String> services = Arrays.asList(
            "http://cms/v3/api-docs",
            "http://auth-server/v3/api-docs"
        );

        return Flux.fromIterable(services)
            .flatMap(url -> webClient.get().uri(url).retrieve().bodyToMono(String.class))
            .collectList()
            .map(this::mergeApiDocs);
    }

    private String mergeApiDocs(List<String> apiDocs) {
        // 合并所有子系统的 API 文档
        // 可以使用 OpenAPI 或 Swagger Parser 将多个文档合并成一个
        // 具体实现需要解析和合并 JSON/YAML 内容
        OpenAPI mergedApi = new OpenAPI();
        Paths combinedPaths = new Paths();

        OpenAPIV3Parser parser = new OpenAPIV3Parser();

        for (String apiDoc : apiDocs) {
            SwaggerParseResult parseResult = parser.readContents(apiDoc, null, null);
            OpenAPI openAPI = parseResult.getOpenAPI();

            if (openAPI != null && openAPI.getPaths() != null) {
                openAPI.getPaths().forEach((path, pathItem) -> {
                    if (combinedPaths.containsKey(path)) {
                        // 如果路径已经存在，可以考虑合并 PathItem 或者覆盖（这里简单覆盖）
                        combinedPaths.put(path, pathItem);
                    } else {
                        combinedPaths.put(path, pathItem);
                    }
                });
            }
        }

        mergedApi.setPaths(combinedPaths);

        // 如果需要，设置其他的 OpenAPI 信息，比如标题、版本等
        mergedApi.setInfo(new io.swagger.v3.oas.models.info.Info()
            .title("Merged API")
            .version("1.0.0"));

        // 将合并后的 OpenAPI 对象转换为 JSON 字符串返回
        return io.swagger.v3.core.util.Json.pretty(mergedApi);
    }
}
