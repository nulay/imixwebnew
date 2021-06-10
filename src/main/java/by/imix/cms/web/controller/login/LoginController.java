package by.imix.cms.web.controller.login;

import by.imix.cms.dao.IRoleDAO;
import by.imix.cms.dao.IUserDAO;
import by.imix.cms.entity.Role;
import by.imix.cms.entity.User;
import by.imix.cms.model.ICmsInfoDAO;
import by.imix.cms.nodedata.Node;
import by.imix.cms.web.form.ConteinerForSession;
import by.imix.cms.web.form.NewUserForm;
import by.imix.cms.web.image.FileUploaderIface;
import by.imix.cms.web.security.CurrentUserWeb;
import by.imix.cms.web.security.UserWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: miha
 * Date: 19.11.13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
@Controller("LoginController")
@SessionAttributes
@Transactional(readOnly = true)
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    //путь к вару на сервере
    private static String webRootPath;
    //путь к файлу аватарке
    private static String avatarPath;

    public static String getWebRootPath() {
        return webRootPath;
    }

    public static void setWebRootPath(String webRootPath) {
        LoginController.webRootPath = webRootPath;
    }

    public static String getAvatarPath() {
        return avatarPath;
    }

    public static void setAvatarPath(String avatarPath) {
        LoginController.avatarPath = avatarPath;
    }

    @Autowired
    private IUserDAO daoUser;
    @Autowired
    private IRoleDAO daoRole;

    @Autowired
    ICmsInfoDAO cmsInfoDAO;

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @RequestMapping({"/cms.html"})
    public String index() {
        return "startPage";
    }


    @RequestMapping(value = "usermanager/gousercabinet.html")
    public ModelAndView gousercabinet() {
        ModelAndView mav = new ModelAndView("cabinetUser");
        Authentication a = null;
        User us = null;
        try {
            a = SecurityContextHolder.getContext().getAuthentication();
            us = ((UserWeb) a.getPrincipal()).getUser();
            mav.addObject("user", us);
            mav.addObject("avatarPath", getFileNameAvatar(avatarPath, us.getName()));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        return mav;
    }

    @Qualifier("imageUploadForAvatar")
    @Autowired
    FileUploaderIface fileUploaderIface;

    @RequestMapping(value = "usermanager/saveimage.html", method = RequestMethod.POST) // Прием файла
    @ResponseBody
    public String saveImage(NewUserForm userForm, @RequestParam(value = "userImage", required = false) MultipartFile image, HttpSession httpSession) {
        try {
            String fileName = "";

            fileUploaderIface.validateImage(image); // Проверить изображение
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            File fOld = null;
            try {
                fOld = fileUploaderIface.getListFileByName(avatarPath, userName)[0];
            } catch (Exception e) {
                //если список пустой - можно конечно проверку на ноль на пустой сделать , но мне лень :)
            }
            if (fOld != null) {
                //удаляем предыдущую аватарку в дальнейшем можно сделать кеширование (ну мало ли кто голую бабу разместит), что бы можно было отчитаться что эт не наш косяк.
                fOld.delete();
            }
            String imOFN = image.getOriginalFilename();
            fileName = userName + imOFN.substring(imOFN.indexOf("."), imOFN.length());
            fileUploaderIface.saveFile(avatarPath, fileName, image); // Сохранить файл
            ((ConteinerForSession) httpSession.getAttribute("contS")).setAvatarPath(getFileNameAvatar(avatarPath, ((ConteinerForSession) httpSession.getAttribute("contS")).getUser().getName()));
            return "<html><head><script>parent.usF.imageLoad(true,'" + fileName + "',null);</script></head><body>Дарова</body></html>";
        } catch (RuntimeException e) {
            //bindingResult.reject(e.getMessage());
            return "<html><head><script>parent.usF.imageLoad(false,null,'" + e.getMessage() + "');</script></head><body>Дарова</body></html>";
        }
    }

    @RequestMapping(value = "usermanager/createnewuser.html")
    public String createNewUser(NewUserForm newUserForm) {
        return "userRegistration";
    }

    // @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "usermanager/createnewuser.html", method = RequestMethod.POST)
    public String saveNewUser(@Valid NewUserForm newUserForm, BindingResult bindingResult, HttpServletRequest request, @CurrentUserWeb UserWeb userWeb) {
        if (bindingResult.hasErrors()) {
            return "userRegistration";
        }
        Node current = userWeb.getUser();
        if (current == null && "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            current = cmsInfoDAO.getInstanceFromDataBase().getNode();
        } else {
            return "userRegistration";
        }
        Role r = daoRole.getFullRoleByName("registeruser");

        User us = daoUser.createUser(newUserForm.createUserFromThis(null), current);

        return "redirect:/usermanager/gousercabinet.html";
    }

    //получаем название файла по имени пользователя
    private String getFileNameAvatar(String folder, String userName) {
        File f = null;
        try {
            f = fileUploaderIface.getListFileByName(folder, userName)[0];
        } catch (Exception e) {
            //или проверку на null
        }
        if (f != null) {
            return f.getName();
        } else {
            return "user0000.gif";
        }
    }

    public IUserDAO getDaoUser() {
        return daoUser;
    }

    public void setDaoUser(IUserDAO daoUser) {
        this.daoUser = daoUser;
    }

    public IRoleDAO getDaoRole() {
        return daoRole;
    }

    public void setDaoRole(IRoleDAO daoRole) {
        this.daoRole = daoRole;
    }
}
