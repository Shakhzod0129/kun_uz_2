package uz.kun.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationResultDTO<T> {
    private long totalSize;
    private List<T> list;

    public PaginationResultDTO() {

    }

    public PaginationResultDTO(long totalSize, List<T> list) {
        this.totalSize = totalSize;
        this.list = list;
    }
}
