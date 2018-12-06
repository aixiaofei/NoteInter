package com.ai.service;

import com.ai.dao.FileDao;
import com.ai.dao.LoveRelationDao;
import com.ai.dao.LoveLittleDao;
import com.ai.dao.Page;
import com.ai.domain.file.File;
import com.ai.domain.user.User;
import com.ai.domain.loveLittle.LoveLittle;
import com.ai.domain.loveRelation.LoveRelation;
import com.ai.service.file.OosFileTencent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LoveService extends BaseService{

    @Autowired
    private LoveRelationDao loveRelationDao;

    @Autowired
    private LoveLittleDao loveLittleDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private OosFileTencent oosFileTencent;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoveService.class);

    /*
     * @Author aixiaofei
     * @Description
     * @Date 12/5/18 7:08 PM
     * @Param [userId]
     * @return com.ai.domain.user.User
     **/
    public User getLoveInfo(int userId){
        return loveRelationDao.getLoveInfo(userId);
    }

    /*
     * @Author aixiaofei
     * @Description
     * @Date 12/5/18 7:08 PM
     * @Param [userId]
     * @return com.ai.domain.loveRelation.LoveRelation
     **/
    public LoveRelation getLoveNumber(int userId){
        return loveRelationDao.getLoveNumber(userId);
    }

    /*
     * @Author aixiaofei
     * @Description 修改双方的爱值
     * @Date 12/5/18 7:07 PM
     * @Param [relations]
     * @return void
     **/
    @Transactional
    public void modifyLoveNumber(List<LoveRelation> relations) {
        loveRelationDao.update(relations);
    }

    public Page getLoveLittleList(Map map) {
        Page page = loveLittleDao.getDataByPage(map);
        List<LoveLittle> loveLittles = (List) page.getData();
        if(loveLittles == null || loveLittles.isEmpty()) {
            return page;
        }
        for(LoveLittle loveLittle: loveLittles) {
            File file = new File();
            file.setFileSource(0);
            file.setFileSourceId(loveLittle.getLittleId());
            List<File> files = fileDao.getList(file);
            loveLittle.setFileInfo(files);
        }
        return page;
    }

    /*
     * @Author aixiaofei
     * @Description
     * @Date 12/5/18 7:07 PM
     * @Param [loveLittle]
     * @return com.ai.domain.loveLittle.LoveLittle
     **/
    public LoveLittle getLoveLittleById(LoveLittle loveLittle) {
        return loveLittleDao.get(loveLittle);
    }

    /*
     * @Author aixiaofei
     * @Description 保存点滴记录
     * @Date 12/5/18 7:06 PM
     * @Param [map] 存放点滴信息和文件信息
     * @return void
     **/
    @Transactional
    public void saveLoveLittle(Map map){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LoveLittle loveLittle = new LoveLittle();
        loveLittle.setFoundUserId(Integer.valueOf(String.valueOf(map.get("foundUserId"))));
        loveLittle.setTitle(String.valueOf(map.get("title")));
        loveLittle.setContent(String.valueOf(map.get("content")));
        try {
            loveLittle.setCreateTime(formatter.parse(String.valueOf(map.get("createTime"))));
            loveLittle.setLastModifyTime(formatter.parse(String.valueOf(map.get("lastModifyTime"))));
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("时间转换错误");
        }
        loveLittle.setLoveNumber(Long.valueOf(String.valueOf(map.get("loveNumber"))));
        loveLittle.setType(Integer.valueOf(String.valueOf(map.get("type"))));
        loveLittle.setImportant(Integer.valueOf(String.valueOf(map.get("important"))));
        loveLittle.setStatus(Integer.valueOf(String.valueOf(map.get("status"))));
        loveLittleDao.save(loveLittle);
        List<Map<String, String>> fileInfo = (List) map.get("file");
        if(fileInfo.isEmpty()){
            return;
        }
        int id = loveLittle.getLittleId();
        List<File> files = new ArrayList<>();
        int fileType = Integer.valueOf(String.valueOf(map.get("fileType")));
        int fileSource = Integer.valueOf(String.valueOf(map.get("fileSource")));
        int fileDerectory = Integer.valueOf(String.valueOf(map.get("fileDerectory")));
        for (Map<String, String> info: fileInfo) {
            File file = new File();
            file.setFileType(fileType);
            file.setFileSource(fileSource);
            file.setFileSourceId(id);
            file.setFileName(info.get("name"));
            file.setFileKey(info.get("key"));
            file.setFileUrl(info.get("url"));
            file.setFileDirectory(fileDerectory);
            files.add(file);
        }
        fileDao.save(files);
    }

    /*
     * @Author aixiaofei
     * @Description 改变点滴状态 接受还是拒绝
     * @Date 12/5/18 7:05 PM
     * @Param [id, action] 点滴Id action=0表示接受 =1表示拒绝
     * @return com.ai.domain.loveRelation.LoveRelation
     **/
    @Transactional
    public LoveRelation responseLoveLittle(int id, int action) {
        LoveLittle bufLoveLittle = new LoveLittle();
        bufLoveLittle.setLittleId(id);
        LoveLittle loveLittle = loveLittleDao.get(bufLoveLittle);
        LoveRelation myRelation = new LoveRelation();
        if(action == 0) {
            LoveRelation relation = loveRelationDao.getLoveNumber(loveLittle.getFoundUserId());
            if(loveLittle.getType() == 0) {
                relation.setMyloveNumber(relation.getMyloveNumber() + loveLittle.getLoveNumber());
                relation.setTotalNumber(relation.getTotalNumber() + loveLittle.getLoveNumber());
            }else {
                if (relation.getLoveNumber() < loveLittle.getLoveNumber()) {
                    relation.setTotalNumber(relation.getTotalNumber() - relation.getLoveNumber());
                    relation.setLoveNumber(0L);
                } else {
                    relation.setLoveNumber(relation.getLoveNumber() - loveLittle.getLoveNumber());
                    relation.setTotalNumber(relation.getTotalNumber() - loveLittle.getLoveNumber());
                }
            }
            myRelation.setMyUserId(relation.getLoveUserId());
            myRelation.setLoveUserId(relation.getMyUserId());
            myRelation.setMyloveNumber(relation.getLoveNumber());
            myRelation.setLoveNumber(relation.getMyloveNumber());
            myRelation.setTotalNumber(relation.getTotalNumber());
            List<LoveRelation> relations = new ArrayList<>(2);
            relations.add(relation);
            relations.add(myRelation);
            loveRelationDao.update(relations);
            loveLittle.setResult(0);
        }else {
            loveLittle.setResult(1);
        }
        loveLittle.setStatus(1);
        loveLittle.setLastModifyTime(new Date());
        loveLittleDao.changeLoveLittleStatus(loveLittle);
        return myRelation;
    }

    /*
     * @Author aixiaofei
     * @Description 删除点滴文件
     * @Date 12/5/18 7:04 PM
     * @Param [id, key] 点滴Id 文件key
     * @return void
     **/
    @Transactional
    public void deleteLittleFile(int id, String key) {
        File file = new File();
        file.setFileSourceId(id);
        file.setFileKey(key);
        fileDao.delete(file);
        oosFileTencent.deleteFile(key);
    }
}
