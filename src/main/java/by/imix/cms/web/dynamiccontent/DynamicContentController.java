package by.imix.cms.web.dynamiccontent;

import by.imix.cms.model.IDynamicContentService;
import by.imix.cms.nodedata.Node;
import by.imix.cms.nodedata.NodeImpl;
import by.imix.cms.nodedata.NodeProperty;
import by.imix.cms.nodedata.service.NodeService;
import by.imix.cms.redirect.RedirectViewService;
import by.imix.cms.web.security.CurrentUserWeb;
import by.imix.cms.web.security.UserWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by miha on 24.09.2014.
 */
@Controller("dynamicContentController")
@Deprecated
public class DynamicContentController {
    private static final Logger logger = LoggerFactory.getLogger(DynamicContentController.class);

    @Autowired
    IDynamicContentService dynamicContentService;

    @Autowired
    @Qualifier("nodeServiceImpl")
    private NodeService<Node, Long> nodeService;

    @Autowired
    private RedirectViewService redirectViewService;

    @RequestMapping(value = "/managercms/dynamiccontent/alldynamiccontents.html")
    public String allTemplates() throws IOException {
        return "manager/dynamicpage/allDynamicContents";
    }

    @RequestMapping(value = "loremIpsum.html")
    public String allTemplatesTest() throws IOException {
        return "blockLoremIpsum";
    }

    @RequestMapping(value = "managercms/dynamiccontent/savedcontent.html", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveDynamicContent(DynamicContent dcForm, HttpServletRequest request, @CurrentUserWeb UserWeb userWeb) throws IOException {
        try {
            if (!dcForm.isClone()) {
                if (dcForm.getId() != null) {
                    Node node = nodeService.getById(dcForm.getId(), false);
                    dcForm.setNode(node);
                }
                dcForm.fillObject();//?????????????????? ???????? ???????????? ??????????????


                //TODO ?????????????????? ?? ????????????????
                //?????????????? ?????? ???????????????? ???????????????????????? ????  cont[
                List<NodeProperty> lnpRem = new ArrayList<NodeProperty>();
                for (NodeProperty np : dcForm.getNode().getNodeProperties()) {
                    if (np.getKeyt().indexOf("cont[") == 0) {
                        lnpRem.add(np);
                    }
                    if (np.getKeyt().indexOf("redirectUrl[") == 0) {
                        lnpRem.add(np);
                    }
                }
                List<NodeProperty> lnpNew = dcForm.getNode().getNodeProperties();
                lnpNew.removeAll(lnpRem);
                dcForm.getNode().setNodeProperties(lnpNew);
            } else {
                ((NodeImpl) dcForm.getNode()).setId(null);
                dcForm.fillObject();//?????????????????? ???????? ???????????? ??????????????
            }
            //?????????????????? ?????????? ???????????????? ??????????????????????
            if (dcForm.getCont() != null) {
                for (int i = 0; i < dcForm.getCont().size(); i++) {
                    Container cont = dcForm.getCont().get(i);
                    String namecont = "cont[" + i + "].";
                    dcForm.getNode().addOnlyOneProperty(namecont + "name", cont.getName());
                    dcForm.getNode().addOnlyOneProperty(namecont + "type", cont.getType());
                    dcForm.getNode().addOnlyOneProperty(namecont + "content", cont.getContent());
                }
            }
            if (dcForm.getRedirectUrl() != null) {
                for (int i = 0; i < dcForm.getRedirectUrl().size(); i++) {
                    String url = dcForm.getRedirectUrl().get(i);
                    String nameRU = "redirectUrl[" + i + "]";
                    dcForm.getNode().addOnlyOneProperty(nameRU, url);
                }
            }
            nodeService.saveOrUpdateNode(dcForm.getNode(), userWeb.getUser());
            redirectViewService.updateRedirectList();
//            File file = new File(request.getSession().getServletContext().getRealPath("/")+"WEB-INF"+File.separator+
//                    "views"+File.separator+"templates"+File.separator+"dynamictemplates"+File.separator+"dyncont_id_"+
//                    dcForm.getId()+".jsp");


            //FileUtils.writeStringToFile(file, dcForm.getFile(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/managercms/dynamiccontent/alldynamiccontents.html");
    }

    @RequestMapping(value = "managercms/dynamiccontent/removedc/{id_cont}", method = RequestMethod.DELETE, produces = "application/json")
    public
    @ResponseBody
    boolean removedc(@PathVariable("id_cont") Long id_cont) throws IOException {
        try {
            nodeService.deleteById(id_cont);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RequestMapping(value = "managercms/dynamiccontent/newdc.html")
    public ModelAndView newdc() throws IOException {
        ModelAndView mav = new ModelAndView("redactDynamicContent");
        mav.addObject("dynamicContent", new DynamicContent());
        return mav;
    }

    @RequestMapping(value = "managercms/dynamiccontent/{idPage}/editdc.html")
    public ModelAndView editdc(@PathVariable("idPage") Long idPage) throws IOException {
        ModelAndView mav = new ModelAndView("redactDynamicContent");
        try {
            Node node = nodeService.getById(idPage, false);
            DynamicContent page = new DynamicContent(node);
            mav.addObject("dynamicContent", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "managercms/dynamiccontent/{idPage}/clonedc.html")
    public ModelAndView cloneDC(@PathVariable("idPage") Long idPage) throws IOException {
        ModelAndView mav = new ModelAndView("redactDynamicContent");
        try {
            Node node = nodeService.getById(idPage, false);
            DynamicContent page = new DynamicContent(node);
            page.setClone(true);
            mav.addObject("dynamicContent", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "content/dynamic/{idPage}/view.html")
    public ModelAndView viewDynamicContent(@PathVariable("idPage") Long idPage) throws IOException {
        ModelAndView mav = new ModelAndView();
        try {
            DynamicContent dynContent = dynamicContentService.load(idPage);
            mav.addObject("dynContent", dynContent);
            logger.debug("?????????????????????? ?????????????????????????? ?????????????????????? {}", dynContent);
            String viewName = dynamicContentService.getViewNameTemplate(dynContent.getNode());
            mav.setViewName(viewName);
        } catch (Exception e) {
            logger.error("???????????? ?????????????????????????? ???????????????? {}", e);
            e.printStackTrace();
        }
        return mav;
    }

    // todo ???????????????????? ?? ??????????????
    @Deprecated
    public ModelAndView viewDynamicContent(ModelAndView mav, Long idSh) throws IOException {
        mav.getViewName();
        try {
            Node node = nodeService.getById(idSh, false);
            DynamicContent dynContent = new DynamicContent(node);
            addConteiners(dynContent, mav);
            if (mav != null) {
                Container contC = new Container();
                contC.setName("centerPage");
                if (mav.getViewName().contains("/")) {
                    contC.setType("jsp");
                } else {
                    contC.setType("src/main/webapp/WEB-INF/tiles");
                }
                contC.setContent(mav.getViewName());
                mav.addObject("centerPage", contC);
            }
            mav.addObject("dynContent", dynContent);
            mav.setViewName("src/main/webapp/WEB-INF/templates/dynamictemplates/template_id_" + dynContent.getShablonName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    private Map<String, Container> getMapNodeName(Node node) {
        Map<String, Container> lNP = new HashMap<String, Container>();
        for (NodeProperty np : node.getNodeProperties()) {
            if (np.getKeyt().startsWith("cont[") && np.getKeyt().endsWith(".name")) {
                Container c = new Container();
                c.setName(np.getValue());
                String num = np.getKeyt().substring(np.getKeyt().indexOf("[") + 1, np.getKeyt().indexOf("]"));
                lNP.put(num, c);
            }
        }
        return lNP;
    }

    //?????????? ?????? ???????????????????? ???????????????? ??????????????????????
    private void addConteiners(DynamicContent page) {
        addConteiners(page, null);
    }

    //?????????? ?????? ???????????????????? ???????????????? ??????????????????????
    private void addConteiners(DynamicContent page, ModelAndView mav) {
        invertNodePToCont(page, mav);
        addPodContainers(page);
    }

    //?????????? ?????? ???????????????????? ???????????????????????? ???????????????????? ?????????????????????? ?? ????????????????????
    private void addPodContainers(DynamicContent page) {
        for (Container c : page.getCont()) {
            if (c.getType().equals("container")) {
                Node nodeB = nodeService.getById(new Long(c.getContent()), false);
                DynamicContent pageCh = new DynamicContent(nodeB);
                pageCh.setContent("src/main/webapp/WEB-INF/templates/dynamictemplates/template_id_" + pageCh.getShablonName());
                addConteiners(pageCh);
                page.addDynamicContent(pageCh);
            }
        }
    }

    //?????????? ?????? ???????????????????????????? node ?? ??????????????????
    private void invertNodePToCont(DynamicContent page) {
        invertNodePToCont(page, null);
    }

    private void invertNodePToCont(DynamicContent page, ModelAndView mav) {
        Map<String, Container> mapCont = getMapNodeName(page.getNode());
        for (NodeProperty np : page.getNode().getNodeProperties()) {
            if (np.getKeyt().startsWith("cont[")) {
                String numCont = np.getKeyt().substring(np.getKeyt().indexOf("[") + 1, np.getKeyt().indexOf("]"));
                Container cont = mapCont.get(numCont);
                String propN = np.getKeyt().substring(np.getKeyt().lastIndexOf(".") + 1, np.getKeyt().length());
                if (propN.equals("name")) {
                    cont.setName(np.getValue()); // ?????????? ??
                } else {
                    if (propN.equals("type")) {
                        cont.setType(np.getValue());
                    } else {
                        if (propN.equals("content")) {
                            cont.setContent(np.getValue());
                        }
                    }
                }
            }
        }
        List<Container> containers = new ArrayList<Container>(mapCont.values());

        page.setCont(containers);
    }

    @RequestMapping(value = "managercms/dynamiccontent/getTemplates", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<NodeProperty> getTemplates(@RequestParam("typet") String typet) throws IOException {
        List<Node> listNode = nodeService.getAllNodeFromPrKey("typeTemplate", typet);
        List<NodeProperty> listTName = new ArrayList<NodeProperty>();
        for (Node n : listNode) {
            n = nodeService.loadFullObject(n);
            listTName.addAll(n.getPropertysValue(n, "name"));
        }

//        <!--# javascript #-->
        return listTName;
    }

    @RequestMapping(value = "managercms/dynamiccontent/getContainers", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<String> getContainers(@RequestParam("id_cont") Long id_cont) throws IOException {
        Node node = nodeService.getById(id_cont, false);
        if (node == null) return Arrays.asList("{err:'not container'}");
        List<String> strings = dynamicContentService.getNamesBlocks(id_cont);
//        List<NodeProperty> properties = node.getPropertysValue(node, "file");
//        if (properties.size() != 0 && null != properties.get(0).getValue()) {
//            return getConteinerFromString(properties.get(0).getValue());
//        }
        return strings;
    }

    private List<String> getConteinerFromString(String strTextFile) {
        Pattern p = Pattern.compile("<\\!--#begin.+#-->", Pattern.MULTILINE);
        Matcher m = p.matcher(strTextFile);
        List<String> str = new ArrayList<String>();
        while (m.find()) {
            String stl = m.group();
            str.add(stl.substring(10, stl.length() - 4).trim());
        }
        return str;
    }

    @RequestMapping(value = "/managercms/dynamiccontent/getDataContainers", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Container> getDataContainers(@RequestParam("id_cont") Long id_cont) throws IOException {
        DynamicContent page = null;
        try {
            Node node = nodeService.getById(id_cont, false);
            page = new DynamicContent(node);
            invertNodePToCont(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page.getCont();
    }

    //?????????? ?????? ???????????????? ???????? DynamicContent ?? json
    @RequestMapping(value = "managercms/dynamiccontent/getAllContainers", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<DynamicContent> getAllContainers() throws IOException {
        List<Node> listNode = nodeService.getAllNodeFromPrKey("type", "dynamicContent");
        List<DynamicContent> ldynCont = new ArrayList<DynamicContent>();
        for (Node n : listNode) {
            n = nodeService.loadFullObject(n);
            DynamicContent dc = new DynamicContent(n);
//            invertNodePToCont(dc);
            ldynCont.add(dc);
        }
        return ldynCont;
    }

    public IDynamicContentService getDynamicContentService() {
        return dynamicContentService;
    }

    public void setDynamicContentService(IDynamicContentService dynamicContentService) {
        this.dynamicContentService = dynamicContentService;
    }

    public NodeService<Node, Long> getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService<Node, Long> nodeService) {
        this.nodeService = nodeService;
    }


    public RedirectViewService getRedirectViewService() {
        return redirectViewService;
    }

    public void setRedirectViewService(RedirectViewService redirectViewService) {
        this.redirectViewService = redirectViewService;
    }
}
