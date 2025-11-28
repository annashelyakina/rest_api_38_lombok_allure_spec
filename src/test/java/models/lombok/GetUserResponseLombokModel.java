package models.lombok;

import lombok.Data;

@Data
public class GetUserResponseLombokModel {
    private GetUserDataLombokModel data;
    private Support support;
    private Meta _meta;

    @Data
    static class Support {
        private String url;
        private String text;
    }

    @Data
    static class Meta {
        private String powered_by;
        private String upgrade_url;
        private String docs_url;
        private String template_gallery;
        private String message;
        private String[] features;
        private String upgrade_cta;
    }
}