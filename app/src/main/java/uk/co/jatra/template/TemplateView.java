package uk.co.jatra.template;

import java.util.List;

public interface TemplateView {
    void setValues(List<String> values);
    void showError(Throwable throwable);
}
