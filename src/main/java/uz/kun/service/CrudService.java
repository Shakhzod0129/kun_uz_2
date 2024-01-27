package uz.kun.service;

public interface CrudService <T, ID>{

    // Yaratish
    T create(T dto);

    // O'qish
    T findById(ID id);

    // O'zgartirish
    Boolean updateById(ID id, T dto);

    // O'chirish
    Boolean deleteById(ID id);
}
