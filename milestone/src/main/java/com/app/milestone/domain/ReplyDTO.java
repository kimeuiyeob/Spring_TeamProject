package com.app.milestone.domain;

import com.app.milestone.entity.People;
import com.app.milestone.entity.Reply;
import com.app.milestone.entity.School;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
@Data
@NoArgsConstructor
public class ReplyDTO {
    private Long replyId;
    private String replyContent;
    private Long schoolUserId;
    private Long peopleUserId;
    private String userName;

    public Reply toEntity() {
        return Reply.builder()
                .replyContent(replyContent)
                .build();
    }

    @QueryProjection
    public ReplyDTO(String replyContent, Long schoolUserId, Long peopleUserId) {
        this.replyContent = replyContent;
        this.schoolUserId = schoolUserId;
        this.peopleUserId = peopleUserId;
    }
}
