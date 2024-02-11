package uz.kun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.kun.dto.ArticleDTO;
import uz.kun.dto.CategoryDTO;
import uz.kun.dto.RegionDTO;
import uz.kun.dto.extra.*;
import uz.kun.entity.*;
import uz.kun.enums.AppLanguage;
import uz.kun.enums.ArticleStatus;
import uz.kun.exception.AppBadException;
import uz.kun.repository.ArticleAndTagNameRepository;
import uz.kun.repository.ArticleItsTypeRepository;
import uz.kun.repository.ArticleRepository;
import uz.kun.repository.TagNameRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleItsTypeRepository articleItsTypeRepository;
    @Autowired
    private ArticleAndItsTypeService articleAndItsTypeService;
    @Autowired
    private ArticleAndTagNameService articleAndTagNameService;
    @Autowired
    private ArticleAndTagNameRepository     articleAndTagNameRepository;
    @Autowired
    private TagNameRepository tagNameRepository;
    @Autowired
    private TagNameService tagNameService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttachService  attachService;


    //(title,description,content,image_id, region_id,category_id, articleType(array))
    public ArticleDTO create(CreatedArticleDto dto, Integer profileId) {

        ArticleEntity entity = new ArticleEntity();

        entity.setTitleUz(dto.getTitleUz());
        entity.setContentUz(dto.getContentUz());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setRegionid(dto.getRegionId());
        entity.setImageId(dto.getPhotoId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setModeratorId(profileId);
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());

        articleRepository.save(entity);

        articleAndItsTypeService.create(entity.getId(), dto.getArticleType());
        articleAndTagNameService.create(entity.getId(), dto.getTagName());

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getId());
        articleDTO.setCreatedDate(entity.getCreatedDate());

        return articleDTO;
    }

    public ArticleDTO update(String articleId, ArticleDTO dto, Integer profileId) {
        ArticleEntity entity = get(articleId);

        RegionEntity regionEntity = regionService.get(dto.getRegionId());
        CategoryEntity categoryEntity = categoryService.get(dto.getCategoryId());
        AttachEntity attachEntity = attachService.get(dto.getImgId());

        entity.setTitleUz(dto.getTitle());
        entity.setContentUz(dto.getContent());
        entity.setDescriptionUz(dto.getDescription());
        entity.setCategoryId(categoryEntity.getId());
        entity.setRegionid(regionEntity.getId());
        entity.setModeratorId(profileId);
        entity.setImageId(attachEntity.getId());
        entity.setStatus(dto.getStatus());
        entity.setViewCount(dto.getViewCount());
        entity.setSharedCount(dto.getSharedCount());

        entity.setUpdatedDate(LocalDateTime.now());
        articleRepository.save(entity);


        articleAndItsTypeService.merge(entity.getId(), dto.getArticleType());
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getId());
        articleDTO.setUpdatedDate(entity.getUpdatedDate());
        return articleDTO;


    }


    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> new AppBadException("Article not found"));
    }


    public Boolean delete(String articleId) {
        ArticleEntity entity = get(articleId);

        Integer delete = articleRepository.deleteArticle(entity.getId());

        return true;
    }

    public Boolean changeStatus(String id, ArticleStatus status) {
        ArticleEntity entity = get(id);
        articleRepository.changeStatus(status, entity.getId());
        return true;
    }


    public ArticleDTO toDTO(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitleUz());
        dto.setContent(entity.getContentUz());
        dto.setDescription(entity.getDescriptionUz());
        dto.setRegionId(entity.getRegionid());
        dto.setCategoryId(entity.getCategoryId());
        dto.setViewCount(entity.getViewCount());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImgId(entity.getImageId());
        return dto;
    }

    public ArticleShortInfoDTO toDTOShort(ArticleEntity entity, Integer typeId) {

        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();

        dto.setTypeId(typeId);
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitleUz());
        dto.setContent(entity.getContentUz());
        dto.setDescription(entity.getDescriptionUz());
        dto.setRegionId(entity.getRegionid());
        dto.setCategoryId(entity.getCategoryId());
        dto.setViewCount(entity.getViewCount());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImgId(entity.getImageId());

        return dto;
    }

    public ArticleShortInfoDTO toDTOShort(ArticleEntity entity) {

        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitleUz());
        dto.setContent(entity.getContentUz());
        dto.setDescription(entity.getDescriptionUz());
        dto.setRegionId(entity.getRegionid());
        dto.setCategoryId(entity.getCategoryId());
        dto.setViewCount(entity.getViewCount());
        dto.setSharedCount(entity.getSharedCount());
        dto.setImgId(entity.getImageId());

        return dto;
    }


    public List<ArticleShortInfoDTO> last5ByType(Integer typeId) {
        List<ArticleAndItsTypeEntity> list = articleItsTypeRepository.findByTypesIdOrderByCreatedDateDesc(typeId);
        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        int count = 0;
        for (ArticleAndItsTypeEntity articleAndItsTypeEntity : list) {

            ArticleEntity entity = articleRepository.findArticleById(articleAndItsTypeEntity.getArticleId());
            if (entity != null) {
                dtoList.add(toDTOShort(entity, typeId));
                count++;
            }
            if (count >= 5) {
                break;  // Agar 3 ta maqola topilgan bo'lsa, tsikl to'xtatiladi
            }
        }
        return dtoList;
    }

//    public List<ArticleShortInfoDTO> last3ByType(Integer typeId) {
//        List<ArticleAndItsTypeEntity> list =
//                articleItsTypeRepository.findByTypesIdOrderByCreatedDateDesc(typeId);
//        int count = 0;
//        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
//        for (ArticleAndItsTypeEntity articleAndItsTypeEntity : list) {
//            ArticleEntity entity =
//                    articleRepository.findArticleById(articleAndItsTypeEntity.getArticleId());
//            if (entity != null) {
//                dtoList.add(toDTOShort(entity, typeId));
//                count++;
//            }
//
//            if (count >= 3) {
//                break;
//            }
//        }
//        return dtoList;
//
//    }

    public List<ArticleShortInfoDTO> last3ByType(Integer typeId) {
        List<ArticleAndItsTypeEntity> list = articleItsTypeRepository.findByTypesIdOrderByCreatedDateDesc(typeId);
        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();

        int count = 0;
        for (ArticleAndItsTypeEntity articleAndItsTypeEntity : list) {

            ArticleEntity entity = articleRepository.findArticleById(articleAndItsTypeEntity.getArticleId());
            if (entity != null) {
                dtoList.add(toDTOShort(entity, typeId));
                count++;
            }
            if (count >= 3) {
                break;  // Agar 3 ta maqola topilgan bo'lsa, tsikl to'xtatiladi
            }
        }
        return dtoList;
    }


    //    public List<ArticleShortInfoDTO> last8ByType(ArticleIdDTO dto) {
//        List<String> idOfArticles = new LinkedList<>();
//        Iterable<ArticleEntity> all = articleRepository.findAllByOrderByCreatedDateDesc();
//        for (ArticleEntity entity : all) {
//            idOfArticles.add(entity.getId());
//        }
//        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
//        int count = 0;
//        for (int i = 0; i < idOfArticles.size(); i++) {
//            if (!dto.getArticleIdList().contains(idOfArticles.get(i))) {
//                ArticleEntity entity = get(idOfArticles.get(i));
//                if (entity != null) {
//                    dtoList.add(toDTOShort(entity));
//                    count++;
//                }
//                if (count >= 8) {
//                    break;
//                }
//            }
//        }
//        return dtoList;
//    }
    public List<ArticleShortInfoDTO> last8ByType(ArticleIdDTO dto) {
        List<String> articleIdList = dto.getArticleIdList();

        List<ArticleEntity> articles = articleRepository.findAllByOrderByCreatedDateDesc();

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        int count = 0;

        for (ArticleEntity entity : articles) {
            // Maqola identifikatorini tekshirish
            if (entity != null && !articleIdList.contains(entity.getId())) {
                dtoList.add(toDTOShort(entity));
                count++;
            }
            // To'plangan maqolalar soni 8 ga yetganda to'xtatish
            if (count >= 8) {
                break;
            }
        }
        return dtoList;
    }


    //    public List<ArticleShortInfoDTO> last4ByType(Integer typeId, String articleId) {
//        List<ArticleAndItsTypeEntity> list =
//                articleItsTypeRepository.findByTypesIdOrderByCreatedDateDesc(typeId);
//        List<String> idOfArticles=new LinkedList<>();
//        for (ArticleAndItsTypeEntity articleAndItsTypeEntity : list) {
//            ArticleEntity entity = get(articleAndItsTypeEntity.getArticleId());
//            idOfArticles.add(entity.getId());
//        }
//        int count=0;
//        List<ArticleShortInfoDTO> dtoList=new LinkedList<>();
//        for (String idOfArticle : idOfArticles) {
//            if(!articleId.equals(idOfArticle)){
//                ArticleEntity entity = get(idOfArticle);
//                if(entity!=null){
//                    dtoList.add(toDTOShort(entity));
//                    count++;
//                }
//                if(count>=4){
//                    break;
//                }
//            }
//        }
//        return dtoList;
//
//    }
    public List<ArticleShortInfoDTO> last4ByType(Integer typeId, String articleId) {
        List<ArticleAndItsTypeEntity> list = articleItsTypeRepository.findByTypesIdOrderByCreatedDateDesc(typeId);

        List<ArticleShortInfoDTO> dtoList = new ArrayList<>();
        int count = 0;

        for (ArticleAndItsTypeEntity articleAndItsTypeEntity : list) {
            if (!articleId.equals(articleAndItsTypeEntity.getArticleId())) {
                ArticleEntity entity = articleRepository.findById(articleAndItsTypeEntity.getArticleId()).orElse(null);
                if (entity != null) {
                    dtoList.add(toDTOShort(entity));
                    count++;
                }
                if (count >= 4) {
                    break;
                }
            }
        }
        return dtoList;
    }


    public List<ArticleShortInfoDTO> getMostRead4Article() {
        List<ArticleEntity> list = articleRepository.findTop4ByOrderByViewCountDesc();

//        List<ArticleShortInfoDTO> dtoList=new LinkedList<>();
//        for (ArticleEntity entity : list) {
//            dtoList.add(toDTOShort(entity));
//        }
//        return dtoList;

        return list.stream()
                .map(this::toDTOShort)
                .collect(Collectors.toList());
    }

    public List<ArticleShortInfoDTO> getByTypeIdAndRegionId(Integer typeId, Integer regionId) {
        List<ArticleAndItsTypeEntity> list = articleItsTypeRepository.findByTypesIdOrderByCreatedDateDesc(typeId);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        int count = 0;
        for (ArticleAndItsTypeEntity articleAndItsTypeEntity : list) {

            ArticleEntity entity = articleRepository.findArticleById(articleAndItsTypeEntity.getArticleId());
            if (entity != null) {
                if (entity.getRegionid().equals(regionId)) {
                    dtoList.add(toDTOShort(entity, typeId));
                    count++;
                }
            }
            if (count >= 5) {
                break;
            }
        }
        return dtoList;
    }

    public PageImpl<ArticleShortInfoDTO> paginationByRegionId(Integer page, Integer size, Integer regionId) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<ArticleEntity> pages = articleRepository.findAllByRegionId(pageable, regionId);

        List<ArticleEntity> content = pages.getContent();
        long totalElements = pages.getTotalElements();

        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();

        for (ArticleEntity entity : content) {
            dtoList.add(toDTOShort(entity));
        }

        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<ArticleShortInfoDTO> paginationByRegionId2(Integer page, Integer size, Integer regionId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<ArticleEntity> pages = articleRepository.findAllByRegionId(pageable, regionId);


        return (PageImpl<ArticleShortInfoDTO>) pages.map(this::toDTOShort);
    }


    public List<ArticleShortInfoDTO> last5ByCategoryId(Integer categoryId) {
        // Maqolalarni topish
        List<ArticleEntity> list = articleRepository.findTop5ByCategoryIdOrderByCreatedDateDesc(categoryId);

        return list.stream()
                .map(this::toDTOShort)
                .collect(Collectors.toList());
    }

    public PageImpl<ArticleShortInfoDTO> paginationByCategoryId(Integer page, Integer size, Integer categoryId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<ArticleEntity> pages = articleRepository.findAllByCategoryId(pageable, categoryId);


        return (PageImpl<ArticleShortInfoDTO>) pages.map(this::toDTOShort);
    }

    public ArticleShortInfoDTO increaseSharedCountByArticleId(String articleId) {
        // Maqolaning ob'ektini topish
        ArticleEntity article = get(articleId);

        // Maqolaning sharedCount attributini o'zgartirish
        article.setSharedCount(article.getSharedCount() + 1);
        articleRepository.save(article);

        // O'zgartirilgan maqolaning ma'lumotlarini ArticleShortInfoDTO ob'ektiga o'girib chiqish
        return toDTOShort(article);
    }

    public ArticleShortInfoDTO increaseViewCountByArticleId(String articleId) {
        // Maqolaning ob'ektini topish
        ArticleEntity article = get(articleId);

        // Maqolaning sharedCount attributini o'zgartirish
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);

        // O'zgartirilgan maqolaning ma'lumotlarini ArticleShortInfoDTO ob'ektiga o'girib chiqish
        return toDTOShort(article);
    }

    public List<ArticleShortInfoDTO> last4ByTagId(Long tagId) {
        List<ArticleAndTagNameEntity> list = articleAndTagNameRepository.findAllByTagsIdOrderByCreatedDateDesc(tagId);

        List<ArticleShortInfoDTO> dtoList=new LinkedList<>();
        int count=0;
        for (ArticleAndTagNameEntity articleAndTagNameEntity : list) {
            ArticleEntity entity = get(articleAndTagNameEntity.getArticleId());
            if(entity!=null){
                dtoList.add(toDTOShort(entity));
                count++;
            }

            if(count>=4){
                break;
            }
        }
        return dtoList;
    }

    public ArticleFullInfoDTO getByLang(AppLanguage language, String articleId) {

        ArticleEntity entity = get(articleId);

        ArticleFullInfoDTO dto=new ArticleFullInfoDTO();

        switch (language){
            case uz -> {
              dto.setTitle(entity.getTitleUz());
              dto.setDescription(entity.getDescriptionUz());
              dto.setContent(entity.getContentUz());
            }
            case ru -> {
                dto.setTitle(entity.getTitleRu());
                dto.setDescription(entity.getDescriptionRu());
                dto.setContent(entity.getContentRu());
            }
            default -> {
                dto.setTitle(entity.getTitleEn());
                dto.setDescription(entity.getDescriptionEn());
                dto.setContent(entity.getContentEn());
            }
        }
        CategoryDTO categoryDTO=new CategoryDTO();
        categoryDTO.setId(entity.getCategoryId());
        categoryDTO.setNameUz(entity.getCategory().getNameUz());
        RegionDTO regionDTO=new RegionDTO();
        regionDTO.setId(entity.getRegionid());
        regionDTO.setNameUz(entity.getRegion().getNameUz());
        dto.setCategory(categoryDTO);
        dto.setSharedCount(entity.getSharedCount());
        dto.setId(entity.getId());
        dto.setViewCount(entity.getViewCount());
        dto.setRegion(regionDTO);
        dto.setPublishedDate(entity.getPublishedDate());

//        List<ArticleAndTagNameEntity> list = articleAndTagNameRepository.findAllByArticleId(articleId);
//        List<String> tagNamesList=new LinkedList<>();
//        for (ArticleAndTagNameEntity articleAndTagNameEntity : list) {
//
//            TagNameEntity tagNameEntity = tagNameService.get(articleAndTagNameEntity.getTagsId());
//            tagNamesList.add(tagNameEntity.getTagName());
//            dto.setTagNames(tagNamesList);
//        }

        List<String> tagNameList = articleAndTagNameRepository.findAllByArticleId(articleId).stream()
                .map(articleAndTagNameEntity -> tagNameService.get(articleAndTagNameEntity.getTagsId()))
                .map(TagNameEntity::getTagName)
                .collect(Collectors.toList());

        dto.setTagNames(tagNameList);
        return dto;
    }
}




