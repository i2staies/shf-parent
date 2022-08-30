package com.itguigu.controller;

import com.itguigu.entity.HouseImage;
import com.itguigu.result.Result;
import com.itguigu.result.ResultCodeEnum;
import com.itguigu.util.QiniuUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    private static final String PAGE_UPLOAD = "house/upload";
    private static final String SHOW_ACTION = "redirect:/house/";

    @Reference
    private HouseImageService houseImageService;

    /**
     * 跳转"house/upload.html页面"
     * @param houseId 房源id
     * @param type 图片类型
     * @return
     */
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Integer type, Model model){
        model.addAttribute("houseId", houseId);
        model.addAttribute("type", type);
        return PAGE_UPLOAD;
    }

    /**
     *
     * @param houseId
     * @param type
     * @param files 批量上传
     * @return
     */
    @PostMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam("file") MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            //设置文件名称
            String prefixName = UUID.randomUUID().toString().replace("-", "");
            String suffixName = file.getOriginalFilename().split("\\.")[1];
            String fileName = prefixName + "." + suffixName;
            //文件上传
            QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
            //将文件信息存储到数据库
            HouseImage houseImage = new HouseImage();
            houseImage.setHouseId(houseId);
            houseImage.setType(type);
            houseImage.setImageName(fileName);
            houseImage.setImageUrl("http://rh5fot5uw.hn-bkt.clouddn.com/" + fileName);
            houseImageService.insert(houseImage);
        }
        return Result.build(null, ResultCodeEnum.FILE_UPLOAD_SUCCESS);
    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id,Model model){
        //从七牛云删除
        HouseImage houseImage = houseImageService.getById(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());

        //后端删除
        houseImageService.delete(id);
        return SHOW_ACTION + houseId;
    }
}
