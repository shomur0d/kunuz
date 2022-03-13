package com.company.Lesson_62;

import com.company.Lesson_62.dto.ArticleDTO;
import com.company.Lesson_62.dto.ArticleFilterDTO;
import com.company.Lesson_62.dto.ProfileDTO;
import com.company.Lesson_62.dto.ProfileFilterDTO;
import com.company.Lesson_62.enums.ProfileRole;
import com.company.Lesson_62.repository.ArticleCustomRepositoryImpl;
import com.company.Lesson_62.service.ArticleService;
import com.company.Lesson_62.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lesson62ApplicationTests {
    @Autowired
    private ProfileService profileService;
	@Autowired
    private ArticleService articleService;
@Autowired
private ArticleCustomRepositoryImpl customRepository;

    @Test
    void contextLoads() {
		/*ProfileDTO dto = new ProfileDTO();
		dto.setName("Vali");
		dto.setSurname("Valiev");
		dto.setLogin("valish");
		dto.setEmail("vali@mail.com");
		dto.setPswd("1234");
		profileService.create(dto);*/

        /*ProfileDTO dto = new ProfileDTO();
        dto.setName("Admin");
        dto.setSurname("Adminjon");
        dto.setLogin("admin");
        dto.setEmail("admin@mail.com");
        dto.setPswd("1234");
        dto.setRole(ProfileRole.ADMIN_ROLE);
        profileService.create(dto);*/

        /*ArticleDTO dto = new ArticleDTO();
        dto.setTitle("Dollar kursi");
        dto.setContent("Dollar kursi pasaymoqda");
//        dto.setProfileId(1);
        articleService.create(dto);*/

        /*ProfileFilterDTO dto = new ProfileFilterDTO();
        dto.setName("Ali");
        System.out.println(profileService.filter(1,10,dto));*/

        ArticleFilterDTO dto = new ArticleFilterDTO();
        dto.setTitle("Salom");

        customRepository.filter2(dto);

    }

}
