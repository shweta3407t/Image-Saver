package com.example.imageSaver.service;

  import com.example.imageSaver.dto.ConverterUploadRequestToImageMetaData;
  import com.example.imageSaver.dto.PaginatedResponseDTO;
  import com.example.imageSaver.dto.UploadImageResponseDTO;
 import com.example.imageSaver.dto.UploadImageRequestDTO;
  import com.example.imageSaver.exception.ResourceNotFoundException;
 import com.example.imageSaver.models.*;
import com.example.imageSaver.repository.CategoryModelRepository;
import com.example.imageSaver.repository.ImageFileUploadRepository;
import com.example.imageSaver.repository.TagModelRepository;
  import net.coobird.thumbnailator.Thumbnails;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
  import org.springframework.data.domain.Page;
  import org.springframework.data.domain.Pageable;
  import org.springframework.stereotype.Service;
  import org.springframework.transaction.annotation.Transactional;
  import org.springframework.web.multipart.MultipartFile;

 import java.io.*;
 import java.nio.file.*;
  import java.time.LocalDate;
  import java.time.LocalDateTime;
  import java.util.ArrayList;
 import java.util.List;
 import java.util.Optional;
import java.util.UUID;
  import java.util.stream.Collectors;


@Service
public class UploadImageService {
    @Autowired
    public ImageFileUploadRepository imageFileUploadRepository;

    @Autowired
    public TagModelRepository tagModelRepository;

    @Autowired
    public CategoryModelRepository categoryModelRepository;

    @Autowired
    public ConverterUploadRequestToImageMetaData convertToListImageResponse;

    Path thumbnailStorageDirectory  = null;
    Path originalStorageDirectory  = null;

    public UploadImageService(@Value("${file.upload-dir}") String uploadDir ) throws IOException {

        Path upath = Paths.get(uploadDir);
        if (!Files.isDirectory(upath)) {
            Files.createDirectory(upath);
        }

        Path thumbStoreDir = upath.resolve("thumbnails");
        Path ogStoreDir = upath.resolve("originals");

        if (!Files.isDirectory(thumbStoreDir)) {
            Files.createDirectory(thumbStoreDir);
        }

        if (!Files.isDirectory(ogStoreDir)) {
            Files.createDirectory(ogStoreDir);
        }


        this.thumbnailStorageDirectory = thumbStoreDir.toAbsolutePath();
        this.originalStorageDirectory = ogStoreDir.toAbsolutePath();
    }

    //save
    @Transactional
    public void saveImageFileRequest(UploadImageRequestDTO uploadImageRequestDTO) throws IOException {
        ImageMetaData uploadImage = new ImageMetaData();


        uploadImage.setCreatedAt(LocalDateTime.now());
        uploadImage.setTitle(uploadImageRequestDTO.getTitle());
        uploadImage.setDescription(uploadImageRequestDTO.getDescription());


        //save and upload category
        String getCategoryName = uploadImageRequestDTO.getCategory();
        Optional<Category> optionalCategory = categoryModelRepository.findByNameIgnoreCase(getCategoryName);

        Category newCategory;
        if (optionalCategory.isPresent()) {
            newCategory = optionalCategory.get();
        } else {
            newCategory = new Category();
            newCategory.setName(getCategoryName);
            categoryModelRepository.save(newCategory);
        }
        uploadImage.setCategory(newCategory);


        //save and upload tag
        String getTagName = uploadImageRequestDTO.getTag();
        Optional<Tag> optionalTag = tagModelRepository.findByNameIgnoreCase(getTagName);

        Tag newTag;
        if (optionalTag.isPresent()) {
            newTag = optionalTag.get();
        } else {
            newTag = new Tag();
            newTag.setName(getTagName);
            tagModelRepository.save(newTag);
        }
        uploadImage.getTag().add(newTag);




         // TODO: create blob file URL
        // http://localhost:8080/files/{file-name}&quality=(low|mid|high)
        MultipartFile multipartFile = uploadImageRequestDTO.getFiles();

        String thumbnailFileName = UUID.randomUUID() + "-thumbnail-" + multipartFile.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID() + "-original-" + multipartFile.getOriginalFilename();

        Path targetedLocationOfOriginal = originalStorageDirectory.resolve(uniqueFileName);
        Files.copy(multipartFile.getInputStream(), targetedLocationOfOriginal, StandardCopyOption.REPLACE_EXISTING);

        uploadImage.setImageUrl(uniqueFileName);



        Path targetedLocationOfThumbnail = thumbnailStorageDirectory.resolve(thumbnailFileName);
        try {
            Thumbnails.of(multipartFile.getInputStream())
                    .scale(1.0)
                    .outputQuality(0.6)
                    .toFile(targetedLocationOfThumbnail.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Files.copy(multipartFile.getInputStream(), targetedLocationOfThumbnail, StandardCopyOption.REPLACE_EXISTING);
        uploadImage.setThumbnailUrl(thumbnailFileName);

        imageFileUploadRepository.save(uploadImage);
        System.out.println("image save in db");
    }



    //search by keyword
    public PaginatedResponseDTO<UploadImageResponseDTO> searchImagesByKeyWord(String keyWord , Pageable pageable){

        Page<UploadImageResponseDTO> page=imageFileUploadRepository.findAllByKeyword(keyWord  , pageable);

        List<UploadImageResponseDTO> dtos=page.getContent()
                .stream()
                .map(image ->
                        new UploadImageResponseDTO(image.getId(),
                                image.getTitle(),
                                image.getDescription() ,
                                image.getTag() ,
                                image.getCategory() ,
                                image.getThumbnailUrl(),
                                image.getCreatedAt(),
                                image.getUpdatedAt())).collect(Collectors.toList());

        //responde
        PaginatedResponseDTO response=new PaginatedResponseDTO();

        response.setContent(dtos);
        response.setCurrentPage(page.getNumber());
        response.setItemLimit(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setHasPrevious(page.hasPrevious());
        response.setHasNext(page.hasNext());

        return  response;
    }



    //all search
    public PaginatedResponseDTO<UploadImageResponseDTO> searchAllImage( Pageable pageable){

        Page<ImageMetaData> page=imageFileUploadRepository.findAllByOrderByCreatedAtAsc( pageable);

        List<UploadImageResponseDTO> dtos=page.getContent()
                .stream()
                .map(image ->
                        new UploadImageResponseDTO(image.getId(),
                                image.getTitle(),
                                image.getTag().toString(),
                                image.getCategory().toString(),
                                image.getThumbnailUrl(),
                                image.getDescription() ,
                                image.getCreatedAt(),
                                image.getUpdatedAt())).collect(Collectors.toList());


        //responde
        PaginatedResponseDTO response=new PaginatedResponseDTO();

        response.setContent(dtos);
        response.setCurrentPage(page.getNumber());
        response.setItemLimit(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setHasPrevious(page.hasPrevious());
        response.setHasNext(page.hasNext());

        return  response;
    }


}





