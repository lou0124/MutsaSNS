package ohchangmin.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListResponse<T> {

    private int count;

    private T data;
}