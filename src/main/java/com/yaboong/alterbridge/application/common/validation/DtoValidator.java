package com.yaboong.alterbridge.application.common.validation;

import com.yaboong.alterbridge.application.api.post.domain.PostCategory;
import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-11
 */
@Component
public class DtoValidator {

    public void validate(PostDto postDto, Errors errors) {
        likeCountCannotExceedViewCount(postDto, errors);
        postCategoryValidation(postDto, errors);
    }

    private void likeCountCannotExceedViewCount(PostDto postDto, Errors errors) {
        Long viewCount = postDto.getViewCount();
        Long likeCount = postDto.getLikeCount();
        boolean invalidCount = likeCount > viewCount;
        if (invalidCount) {
            errors.rejectValue("likeCount", "WrongValue", "Like counts cannot be greater than view counts");
            errors.rejectValue("viewCount", "WrongValue", "View counts cannot be less than like counts");
            errors.reject("wrongCounts", "counts are invalid");
        }
    }

    private void postCategoryValidation(PostDto postDto, Errors errors) {
        String category = postDto.getCategory();
        boolean isValidCategory = EnumUtils.isValidEnum(PostCategory.class, category);
        if (!isValidCategory) {
            errors.rejectValue("category", "invalidCategory", "Requested category of post not exists");
        }
    }

}
