package com.stoneistudio.lds.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private Long productId;
    private String productName;
    private String categoryName;
    private List<String> comments;
    private List<QAItem> qaItems;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QAItem {
        private String question;
        private String answer;
    }
}
